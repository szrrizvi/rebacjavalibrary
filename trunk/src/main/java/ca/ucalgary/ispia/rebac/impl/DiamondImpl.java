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

import ca.ucalgary.ispia.rebac.Diamond;
import ca.ucalgary.ispia.rebac.Direction;
import ca.ucalgary.ispia.rebac.Policy;

/**
 * @author Syed Zain Rizvi
 */

/**
 * A concrete implementation of {@link Diamond}
 * @see ca.ucalgary.ispia.rebac.Diamond
 */
public final class DiamondImpl implements Diamond {

	/**
	 * The policy to relate the accessor.
	 */
	public final Policy policy;
	/**
	 * The relation identifier for the policy.
	 */
	public final Object relationIdentifier;
	/**
	 * The relationship direction indicator.
	 */
	public final Direction direction;
	
	/**
	 * Initializes field members.<p>
	 * @param policy The policy to relate the accessor.
	 * @param relationIdentifier The relation identifier
	 * @param direction The indicator for the direction of relationship.
	 */
	public DiamondImpl (Policy policy, Object relationIdentifier, Direction direction){
		this.policy = policy;
		this.relationIdentifier = relationIdentifier;
		this.direction = direction;
	}

	/**
	 * @return The contained policy
	 * @see ca.ucalgary.ispia.rebac.Box#getPolicy()
	 */
	@Override
	public Policy getPolicy() {
		return this.policy;
	}

	/**
	 * @return The object representing the relation identifier
	 * @see ca.ucalgary.ispia.rebac.Box#getRelationIdentifier()
	 */
	@Override
	public Object getRelationIdentifier() {
		return this.relationIdentifier;
	}

	/**
	 * @return The direction specified by this object
	 * @see ca.ucalgary.ispia.rebac.Box#getDirection()
	 */
	@Override
	public Direction getDirection() {
		return this.direction;
	}
	
	/**
	 * Prints out a human readable version of this object.
	 * @return A human readable version of this object
	 * @see ca.ucalgary.ispia.rebac.Policy#toString()
	 */	
	@Override
	public String toString(){
		String str = "";
		
		str = str + ("(<");
		
		switch(direction){
		case FORWARD:	break;
		case BACKWARD:	str = str + ("-");
						break;
		case EITHER:	str = str + ("%");
		}
		
		str = str + (relationIdentifier + "> ");
		str = str + policy;				// print contained policy
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
		result = prime * result + "Diamond".hashCode();
		result = prime * result
				+ ((direction == null) ? 0 : direction.hashCode());
		result = prime * result + ((policy == null) ? 0 : policy.hashCode());
		result = prime * result + ((relationIdentifier == null) ? 0 : relationIdentifier.hashCode());
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
			return true;			// Compare pointers
		}
		if (obj == null) {
			return false;			// False if obj is null
		}
		if (!(obj instanceof DiamondImpl)) {
			return false;			// False is obj is not of same instance
		}
		DiamondImpl other = (DiamondImpl) obj;
		if (direction != other.direction) {
			return false;			// False is direction does not match
		}
		if (policy == null) {
			if (other.policy != null) {
				return false;		// False if contained policy is null, and obj's policy is not null
			}
		} else if (!policy.equals(other.policy)) {
			return false;			// False if policy does not match
		}
		
		if (relationIdentifier == null){
			if (other.relationIdentifier != null){
				return false;		// False if contained relation identifier is null, and obj's relation identifier is not null
			}
		} else if (!relationIdentifier.equals(other.relationIdentifier)) {
			return false;			// False if relationIdentifier does not match
		}
		return true;				// If reached here, true
	}
}
