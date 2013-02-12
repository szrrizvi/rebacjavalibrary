package ca.ucalgary.ispia.rebac;


/**
 * @author Syed Zain Rizvi
 */

/**
 *  Disjunction variant for the interface {@link Policy}. Asserts the disjunction of two
 *  policies.
 */
public interface Disjunction extends Policy{

	/**
	 * @return The first contained policy
	 */
	public Policy getPolicyA();
	
	/**
	 * @return The second contained policy
	 */
	public Policy getPolicyB();
}
