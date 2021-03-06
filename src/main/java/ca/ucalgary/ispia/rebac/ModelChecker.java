/**
 * Copyright (c) 2013 ReBAC Java Library
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated
 * documentation files (the "Software"), to deal in the Software without restriction, including without limitation the
 * rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit
 * persons to whom the Software is furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the
 * Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 * WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package ca.ucalgary.ispia.rebac;

import java.util.HashSet;
import java.util.Set;

import ca.ucalgary.ispia.rebac.util.Constants;
import ca.ucalgary.ispia.rebac.util.Triple;
import ca.ucalgary.ispia.rebac.util.Cache;
import ca.ucalgary.ispia.rebac.util.PolicyUtil;

/**
 * @author Syed Zain Rizvi
 * @author Mona
 */

/**
 * A memoized model checker.
 */

public class ModelChecker {
	/**
	 * Finds if the given access is allowed. Assumes that the given policy is in
	 * simplest form.
	 * @param cache
	 * @param frame The protection state
	 * @param vc The variable convention
	 * @param u The starting point
	 * @param v The end point
	 * @param policy The policy to be checking
	 * @return True if the access is allowed by the policy, else false
	 */
	public static boolean check(Cache<Triple<Policy, Environment, Object>, Boolean> cache,
			Frame frame, Constants.VariableConvention vc, Object u, Object v, Policy policy) {
		
		Environment g = null;
		
		// Provide variable names based on the variable convention
		switch(vc){
		case OwnerRequestor:
			g = Environment.insert(Constants.owner, u, g);
			g = Environment.insert(Constants.requestor, v, g);
			break;
		case ResourceRequestor:
			g = Environment.insert(Constants.resource, u, g);
			g = Environment.insert(Constants.requestor, v, g);
			break;
		default:
			break;
		}
		
		// Check if the policy can be satisfied
		boolean result = check(cache, frame, g, u, policy);
		return result;
		
	}
	
	/**
	 * Finds if the given access is allowed. Assumes that the given policy is in
	 * simplest form.
	 * @param cache
	 * @param frame The protection state
	 * @param uName The variable name for u
	 * @param u The starting point
	 * @param vName The variable name for v
	 * @param v The end point 
	 * @param policy The policy to be checking
	 * @return True if the access is allowed by the policy, else false.
	 */
	public static boolean check(Cache<Triple<Policy, Environment, Object>, Boolean> cache, 
			Frame frame, Object uName, Object u, Object vName, Object v, Policy policy){
		
		// Construct the environment
		Environment g = null;
		g = Environment.insert(uName, u, g);
		g = Environment.insert(vName, v, g);
		
		// Check if the policy can be satisfied
		boolean result = check(cache, frame, g, u, policy);
		return result;
				
	}
	
	
	/**
	 * Finds if the given access is allowed. Assumes that the given policy is in
	 * simplest form.
	 * @param cache
	 * @param frame The protection state
	 * @param g The pre-constructed environment. The valuation
	 * @param u The starting point
	 * @param policy The policy to be checking
	 * @return True if the access is allowed by the policy, else false.
	 */
	public static boolean check(Cache<Triple<Policy, Environment, Object>, Boolean> cache, 
			Frame frame, Environment g, Object u, Policy policy){
		
		// Call checker
		
		boolean result = checker(cache, frame, u, g, policy);
		return result;
				
	}
	
	/**
	 * The actual checker. Finds if the given access is allowed.
	 * @param cache: caches the results obtained for a pair of (policy, Object)
	 * @param frame: model The protected state
	 * @param s: local node S
	 * @param g: A valuation
	 * @param policy: The policy to be checking
	 * @return True if the access is allowed by the policy, else false.
	 */
	private static boolean checker(Cache<Triple<Policy, Environment, Object>, Boolean> cache, 
			Frame frame, Object s, Environment g, Policy policy) {
		Triple<Policy, Environment, Object> triple = new Triple<Policy, Environment, Object>(policy, g, s);
		if (cache.check_existance(triple))
			return (Boolean) cache.retrieve(triple);
		else {
			boolean result;
			// Find the specific policy
			
			if (policy instanceof Variable){
				
				Variable tempPolicy = (Variable) policy;
				Object temp = tempPolicy.getVariable();
				
				// Check if the variable has been bound to a state.
				result = (s.equals(Environment.apply(g, temp)) ? true : false);
		
				cache.put(triple, result);
				return result;
				
			}
			
			else if(policy instanceof True) {
				result = true;
				cache.put(triple, result);
				return result;
			}
			
			else if(policy instanceof False) {
				result = false;
				cache.put(triple, result);
				return result;
			}
			
			else if (policy instanceof Negation) {
				Negation tempPolicy = (Negation) policy;
				// For the negation policy, return the negation of the contained policy.
				result = !(checker(cache, frame, s, g, tempPolicy.getPolicy()));
				cache.put(triple, result);
				return result;	
			}
			
			else if (policy instanceof Conjunction) {
				
				Conjunction tempPolicy = (Conjunction) policy;
				result = checker(cache , frame, s, g, tempPolicy.getPolicyA()) && checker(cache, frame, s, g, tempPolicy.getPolicyB());
				cache.put(triple, result);
				return result;
			}
			
				
			else if (policy instanceof Diamond) {
				Diamond tempPolicy = (Diamond) policy;
				// For the diamond policy
				// Get all specified neighbours of the resource

				Iterable<Object> itr = frame.findNeighbours(s, tempPolicy.getRelationIdentifier(),
				    tempPolicy.getDirection());
				// Loop through all neighbours
				for (Object neighbour : itr) {
					// Test if the contained policy holds for the neighbour
					if (checker(cache, frame, neighbour, g, tempPolicy.getPolicy())) {
						result = true;
						cache.put(triple, result);
						return result;
					}
				}
				
				result = false;
				cache.put(triple, result);
				return result;
				
			}  
			
			else if (policy instanceof At) {
				// For the At policy
				//jump to the state named by the variable
				At tempPolicy = (At) policy;
				Object tempA = tempPolicy.getVariable();
				Object t = Environment.apply(g, tempA);
				
				//check if the variable in At policy has been already bound to a state
				if (t != null) {
					result = checker(cache, frame, t, g, tempPolicy.getPolicy());
					cache.put(triple, result);
					return result;
				}
				else throw new IllegalArgumentException("The variable "+tempA+" has not been bound to a state");
			}
			
			else if (policy instanceof Bind){
				//For the Binder policy
				//Bind variable to the current state
				Bind tempPolicy = (Bind) policy;
				Object tempA = tempPolicy.getVariable();
								
				Set<Object> free_vars = PolicyUtil.findFreeVars(tempPolicy, new HashSet<Object>());
				g = Environment.project(g, free_vars);
				
				Environment h = Environment.insert(tempA, s, g);
				
				result = checker(cache, frame, s, h, tempPolicy.getPolicy());
				cache.put(triple, result);
				return result;
			}
			
			else {
				// Else (not a supported policy variant), throw exception
				throw new IllegalArgumentException("The given policy is not supported by the Model Checker: " + policy);
			}
			
		}
	}
}
