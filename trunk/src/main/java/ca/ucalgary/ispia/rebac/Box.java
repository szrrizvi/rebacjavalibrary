package ca.ucalgary.ispia.rebac;

/**
 *  Box variant for the interface {@link Policy}. Asserts that every individual
 *  related to the owner via a certain relationship (identified by the 
 *  relation identifier) is also related to the requestor in a manner specified
 *  by a certain policy (identified by the contained policy). <p>
 *  Derived from the variants {@link Negation} and {@link Diamond}.
 */
public interface Box extends Policy {

	/**
	 * @return The contained policy
	 */
	public Policy getPolicy();
	
	/**
	 * @return The object representing the relation identifier
	 */
	public Object getRelationIdentifier();
	
	/**
	 * @return The direction specified by this object
	 */
	public Direction getDirection();
}
