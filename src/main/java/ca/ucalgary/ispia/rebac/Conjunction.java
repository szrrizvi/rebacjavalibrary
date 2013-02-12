package ca.ucalgary.ispia.rebac;

/**
 * @author Syed Zain Rizvi
 */

/**
 *  Conjunction variant for the interface {@link Policy}. Asserts the conjunction of two
 *  policies.<p>
 *  Derived from variants {@link Disjunction} and {@link Negation}.
 */
public interface Conjunction extends Policy {

	/**
	 * @return The first contained policy
	 */
	public Policy getPolicyA();
	
	/**
	 * @return The second contained policy
	 */
	public Policy getPolicyB();
}
