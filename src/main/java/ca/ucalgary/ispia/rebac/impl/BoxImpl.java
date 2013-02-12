package ca.ucalgary.ispia.rebac.impl;

import ca.ucalgary.ispia.rebac.Box;
import ca.ucalgary.ispia.rebac.Direction;
import ca.ucalgary.ispia.rebac.Policy;

/**
 * @author Syed Zain Rizvi
 */

/**
 * A concrete implementation of {@link Box}.
 * @see ca.ucalgary.ispia.rebac.Box
 */
public final class BoxImpl implements Box {

	/**
	 * The policy to relate the requestor.
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
	 * @param policy The policy to relate the requestor.
	 * @param relationIdentifier The relation identifier.
	 * @param direction The indicator for the direction of relationship.
	 */
	public BoxImpl (Policy policy, Object relationIdentifier, Direction direction){
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
		
		str = str + ("([");
		
		switch (direction){
		case FORWARD:	break;
		case BACKWARD:	str = str + ("-");
						break;
		case EITHER:	str = str + ("%");
						break;
		}
		
		str = str + (relationIdentifier + "] ");
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
		result = prime * result + "Box".hashCode();
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
		if (!(obj instanceof BoxImpl)) {
			return false;			// False is obj is not of same instance
		}
		BoxImpl other = (BoxImpl) obj;
		if (direction != other.direction) {
			return false;			// False if direction does not match
		}
		if (policy == null) {
			if (other.policy != null) {
				return false;		// False if contained policy is null, and obj's policy is not null
			}
		} else if (!policy.equals(other.policy)) {
			return false;			// False if contained policy does not match
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

