package ca.ucalgary.ispia.rebac;


/**
 * @author Syed Zain Rizvi
 */

/**
 *  Negation variant for the interface {@link Policy}. Asserts the negation
 *  of a certain policy (identified by the contained policy).
 */
public interface Negation extends Policy{

	/**
	 * @return The contained policy
	 */
	public Policy getPolicy();
}
