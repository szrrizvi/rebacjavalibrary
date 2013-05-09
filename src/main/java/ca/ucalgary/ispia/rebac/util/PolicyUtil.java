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

package ca.ucalgary.ispia.rebac.util;

import ca.ucalgary.ispia.rebac.Direction;
import ca.ucalgary.ispia.rebac.Policy;
import ca.ucalgary.ispia.rebac.impl.BoxImpl;
import ca.ucalgary.ispia.rebac.impl.ConjunctionImpl;
import ca.ucalgary.ispia.rebac.impl.DiamondImpl;
import ca.ucalgary.ispia.rebac.impl.DisjunctionImpl;
import ca.ucalgary.ispia.rebac.impl.FalseImpl;
import ca.ucalgary.ispia.rebac.impl.NegationImpl;
import ca.ucalgary.ispia.rebac.impl.RequestorImpl;
import ca.ucalgary.ispia.rebac.impl.TrueImpl;

	/**
	 * @author Syed Zain Rizvi
	 */

	/**
	 * Offers utility methods for Policies
	 */
	public class PolicyUtil {
		
		/**
		 * Consumes a policy and translates it to a policy only composed of primitive
		 * operators. NOTE: If the given policy is already only composed of primitive
		 * operators, it is returned as is.
		 * Primitive Operators: {@link RequestorImpl}, {@link DiamondImpl}, {@link DisjunctionImpl},
		 * {@link NegationImpl}, and {@link TrueImpl}
		 * @param tPolicy The policy to be translated.
		 * @return A policy composed of only primitive operators.
		 */
		public static Policy translate(Policy tPolicy){
			Policy translatedPolicy;
			
			if (tPolicy == RequestorImpl.getInstance()){
				// For requestor variant
				translatedPolicy = tPolicy;
			}
			
			else if (tPolicy instanceof BoxImpl){
				// Box(policy) = Negation(Diamond(Negation(policy)))
	
				BoxImpl temp = (BoxImpl) tPolicy;
				
				// Get contained fields
				Policy tempA = temp.policy;
				Object relationID = temp.relationIdentifier;
				Direction direction = temp.direction;
				// Translate contained policy
				tempA = new NegationImpl (translate(tempA));
				// Create translated policy
				Policy tempB = new DiamondImpl(tempA, relationID, direction);		
				translatedPolicy = new NegationImpl (tempB);
			}
			
			else if (tPolicy instanceof ConjunctionImpl){
				// Conjunction(policyA, policyB) =
				//						Negation(Disjunction((Negation(policyA)), (Negation(policyB))))
				
				ConjunctionImpl temp = (ConjunctionImpl) tPolicy;
				// Get contained fields
				Policy tempA = temp.policyA;
				Policy tempB = temp.policyB;
				// Translate contained policies
				tempA = new NegationImpl (translate(tempA));
				tempB = new NegationImpl (translate(tempB));
				// Create translated policy
				Policy tempC = new DisjunctionImpl(tempA, tempB);
				translatedPolicy = new NegationImpl (tempC);
				
			}
					
			else if (tPolicy instanceof DiamondImpl){
				// Already primitive. Recurse on contained policy.
				
				DiamondImpl temp = (DiamondImpl) tPolicy;
				// Get contained fields
				Policy tempA = temp.policy;
				Object relationID = temp.relationIdentifier;
				Direction direction = temp.direction;
				// Translate contained policy
				tempA = translate(tempA);
				// Create translated policy
				translatedPolicy = new DiamondImpl(tempA, relationID, direction);
			}
					
			else if (tPolicy instanceof DisjunctionImpl){
				// Already primitive. Recurse on contained policies.
				
				DisjunctionImpl temp = (DisjunctionImpl) tPolicy;
				// Get contained fields
				Policy tempA = temp.policyA;
				Policy tempB = temp.policyB;
				// Translate contained fields
				tempA = translate(tempA);
				tempB = translate(tempB);
				// Create translated policy
				translatedPolicy = new DisjunctionImpl(tempA, tempB);
			}
			
			else if (tPolicy == FalseImpl.getInstance()){
				// False = Negation(True)
				
				// Create translated policy
				translatedPolicy = new NegationImpl(TrueImpl.getInstance());
			}
			
			else if(tPolicy instanceof NegationImpl){
				// Already primitive. Recurse on contained policy.
				NegationImpl temp = (NegationImpl) tPolicy;
				// Get contained fields
				Policy tempA = temp.policy;
				// Translate contained policy
				tempA = translate(tempA);
				// Create translated policy
				translatedPolicy = new NegationImpl (tempA);
			} 
			
			else if (tPolicy == TrueImpl.getInstance()){
				// For true variant
				translatedPolicy = tPolicy;
			}
			
			else {
				// Else (not a supported policy variant), throw exception
				throw new IllegalArgumentException("The given policy is not supported by the Translate method: " + tPolicy);
			}
			
			return translatedPolicy;
		}	
}
