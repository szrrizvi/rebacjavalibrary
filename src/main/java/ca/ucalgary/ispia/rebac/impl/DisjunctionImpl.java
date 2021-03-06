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

package ca.ucalgary.ispia.rebac.impl;

import ca.ucalgary.ispia.rebac.Disjunction;
import ca.ucalgary.ispia.rebac.Policy;

/**
 * @author Syed Zain Rizvi
 */

/**
 * A concrete implementation of {@link Disjunction}
 * @see ca.ucalgary.ispia.rebac.Disjunction
 */
public final class DisjunctionImpl implements Disjunction {
	
	/**
	 * The first policy to be considered.
	 */
	public final Policy policyA;
	/**
	 * The second policy to be considered.
	 */
	public final Policy policyB;
	
	/**
	 * Initializes the field members.
	 * @param policyA The first policy to be considered.
	 * @param policyB The second policy to  be considered.
	 */
	public DisjunctionImpl (Policy policyA, Policy policyB){
		this.policyA = policyA;
		this.policyB = policyB;
	}

	/**
	 * @return The first contained policy
	 * @see ca.ucalgary.ispia.rebac.Disjunction#getPolicyA()
	 */
	@Override
	public Policy getPolicyA() {
		return this.policyA;
	}

	/**
	 * @return The second contained policy
	 * @see ca.ucalgary.ispia.rebac.Disjunction#getPolicyB()
	 */
	@Override
	public Policy getPolicyB() {
		return this.policyB;
	}
	
	/**
	 * Prints out a human readable version of this object.
	 * @return A human readable version of this object
	 * @see ca.ucalgary.ispia.rebac.Policy#toString()
	 */
	@Override
	public String toString(){
		String str = "";
		
		str = str + ("(");
		str = str + policyA;		// print contained policyA
		str = str + (" or ");
		str = str + policyB;		// print for contained policyB
		str = str + (")");
		
		return str;
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
		result = prime * result + "Disjunction".hashCode();
		result = prime * result + ((policyA == null) ? 0 : policyA.hashCode());
		result = prime * result + ((policyB == null) ? 0 : policyB.hashCode());
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
		if (!(obj instanceof DisjunctionImpl)) {
			return false;		// False is obj is not of same instance
		}
		DisjunctionImpl other = (DisjunctionImpl) obj;
		if (policyA == null) {
			if (other.policyA != null) {
				return false;	// False if contained policyA is null and obj's policyA is not null
			}
		} else if (!policyA.equals(other.policyA)) {
			return false;		// False if policyA does not match
		}
		if (policyB == null) {
			if (other.policyB != null) {
				return false;	// False if contained policyB is null and obj's policyB is not null
			}
		} else if (!policyB.equals(other.policyB)) {
			return false;		// False if policyB does not match
		}
		return true;			// If reached here, true
	}
}
