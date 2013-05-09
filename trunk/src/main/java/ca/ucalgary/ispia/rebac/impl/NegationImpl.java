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

package ca.ucalgary.ispia.rebac.impl;

import ca.ucalgary.ispia.rebac.Negation;
import ca.ucalgary.ispia.rebac.Policy;


/**
 * @author Syed Zain Rizvi
 */

/**
 * A concrete implementation of {@link Negation}
 * @see ca.ucalgary.ispia.rebac.Negation
 */
public final class NegationImpl implements Negation {

	/**
	 * The policy whose negation is asserted.
	 */
	public final Policy policy;
	
	/**
	 * Initialize the field members.
	 * @param policy The policy whose negation is asserted.
	 */
	public NegationImpl (Policy policy){
		this.policy = policy;
	}
	
	/**
	 * @return The contained policy
	 * @see ca.ucalgary.ispia.rebac.Box#getPolicy()
	 */
	public Policy getPolicy() {
		return this.policy;
	}
	
	/**
	 * Returns the hash code of this object
	 * @return The hash code of this object
	 * @see ca.ucalgary.ispia.rebac.Policy#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + "Negation".hashCode();
		result = prime * result + ((policy == null) ? 0 : policy.hashCode());
		return result;
	}


	/**
	 * Checks if this object is equal to the given object.
	 * @return True if this object is equal to the given object, else false.
	 * @see ca.ucalgary.ispia.rebac.Policy#equals(Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;		// Compare pointers
		}
		if (obj == null) {
			return false;		// False if obj is null
		}
		if (!(obj instanceof NegationImpl)) {
			return false;		// False if obj is not of same instance
		}
		NegationImpl other = (NegationImpl) obj;
		if (policy == null) {
			if (other.policy != null) {
				return false;	// False if contained policy is null and obj's policy is not null
			}
		} else if (!policy.equals(other.policy)) {
			return false;		// False if policy does not match
		}
		return true;			// If reached here, true
	}



	/**
	 * Prints out a human readable version of this object.
	 * @return A human readable version of this object
	 * @see ca.ucalgary.ispia.rebac.Policy#toString()
	 */	
	@Override
	public String toString (){
		String str = "(not ";
		str = str + policy; 		// print contained policy
		str = str + ")";
		return str;
	}
}
