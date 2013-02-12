package ca.ucalgary.ispia.rebac;

/**
 *  Diamond variant for the interface {@link Policy}. Asserts that the owner is related
 *  to an individual via a certain relationship (identified by relation identifier),
 *  and this individual is related to the requestor in a manner specified by a certain policy
 *  (identified by the contained policy).
 */
public interface Diamond extends Policy{

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
