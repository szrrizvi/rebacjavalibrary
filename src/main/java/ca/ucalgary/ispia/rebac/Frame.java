package ca.ucalgary.ispia.rebac;

/**
 * @author Syed Zain Rizvi
 */

/**
 * Provides a representation-independent way for model checking algorithm to
 * interact with the protection state (i.e., the social networks and the
 * context hierarchy).
 */
public interface Frame {
	
	/**
	 * Finds all the specific type neighbours of the given vertex, in the effective
	 * social network of the context.<br>
	 * <b>Note:</b> It is up to the programmer to establish contexts.
	 * @param vertex The vertex to find the neighbours of.
	 * @param relationIdentifier The relation identifier.
	 * @return Specific type neighbours of the given vertex in the effective social
	 * network.
	 */
	public Iterable findNeighbours(Object vertex, Object relationIdentifier, Direction direction);
}