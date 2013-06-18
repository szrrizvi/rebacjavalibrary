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
import ca.ucalgary.ispia.rebac.util.SimpleCache;
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
	 * @param frame The protected state
	 * @param resource The resource 
	 * @param requestor The resource requestor
	 * @param policy The policy to be checking
	 * @return True if the access is allowed by the policy, else false.
	 */
	@SuppressWarnings("rawtypes")
	public static boolean check(Cache cache, Frame frame, Object owner, Object requestor, Policy policy) {
		// Initialize cache
		//Cache<Triple, Boolean> cache = new Cache<Triple, Boolean>();
		Environment g = null;
		
		g = Environment.insert(Constants.owner, owner, g);
		g = Environment.insert(Constants.requestor, requestor, g);
		
		// Call checker
		boolean result = checker(cache, frame, owner, g, policy);
		//System.out.println("cache size:	" + cache.get_size());
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
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private static boolean checker(Cache cache, Frame frame, Object s, Environment g, Policy policy) {
		
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
				//System.out.println(s+ "		" + g + "	" + temp + "	" + policy);
				result = ((Environment.apply(g, temp).equals(s))? true : false);
				//System.out.println(result);
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
								
				Set free_vars = PolicyUtil.findFreeVars(tempPolicy, new HashSet<Object>());
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
