/**
 * Copyright (c) 2013 Syed Zain Rizvi
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

import java.util.HashMap;
import java.util.Map;

import ca.ucalgary.ispia.rebac.util.Pair;

/**
 * @author Syed Zain Rizvi
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
	public static boolean check(Frame frame, Object resource, Object requestor, Policy policy) {
		// Initialize map
		Map<Pair<Policy, Object>, Boolean> map = new HashMap<Pair<Policy, Object>, Boolean>();
		// Call checker
		boolean result = checker(map, frame, resource, requestor, policy);
		
		return result;
	}
	
	/**
	 * The actual checker. Finds if the given access is allowed.
	 * @param model The protected state
	 * @param resource The resource
	 * @param requestor The resource requestor
	 * @param policy The policy to be checking
	 * @return True if the access is allowed by the policy, else false.
	 */
	@SuppressWarnings("unchecked")
	private static boolean checker(Map<Pair<Policy, Object>, Boolean> map, Frame frame, Object resource, Object requestor, Policy policy) {
		
		Pair<Policy, Object> pair = new Pair<Policy, Object>(policy, resource);
		if (map.containsKey(pair)) {
			return map.get(pair);
		} else {
			boolean result;
			// Find the specific policy
			if (policy instanceof Requestor) {
				// For assertion policy, check if the resource is the requestor
				
				result = resource.equals(requestor);
				map.put(pair, result);
				return result;
				
			} else if (policy instanceof Diamond) {
				Diamond tempPolicy = (Diamond) policy;
				// For the diamond policy
				// Get all specified neighbours of the resource

				Iterable<Object> itr = frame.findNeighbours(resource, tempPolicy.getRelationIdentifier(),
				    tempPolicy.getDirection());
				
				// Loop through all neighbours
				for (Object neighbour : itr) {

					// Test if the contained policy holds for the neighbour
					if (checker(map, frame, neighbour, requestor, tempPolicy.getPolicy())) {
						result = true;
						map.put(pair, result);
						return result;
					}
				}
				
				result = false;
				map.put(pair, result);
				return result;
				
			} else if (policy instanceof Disjunction) {
				
				Disjunction tempPolicy = (Disjunction) policy;
				// For the disjunction policy Checks if either one (or both) of the contained policies
				// are true.
				boolean checkA = checker(map, frame, resource, requestor, tempPolicy.getPolicyA());
				
				if (checkA){
					result = true;
					map.put(pair, result);
					return result;
				}
				
				boolean checkB = checker(map, frame, resource, requestor, tempPolicy.getPolicyB());
				
				if (checkB){
					result = true;
					map.put(pair, result);
					return result;
				}
				
				result = false;
				map.put(pair, result);
				return result;

				
			} else if (policy instanceof True) {
				// For the True policy return true
				result = true;
				map.put(pair, result);
				return result;
				
			} else if (policy instanceof Negation) {
				Negation tempPolicy = (Negation) policy;
				// For the negation policy, return the negation of the contained policy.
				result = !(checker(map, frame, resource, requestor, tempPolicy.getPolicy()));
				map.put(pair, result);
				return result;
				
			} else {
				// Else (not a supported policy variant), throw exception
				throw new IllegalArgumentException("The given policy is not supported by the Model Checker: "
				        + policy);
			}
		}
	}

	
}
