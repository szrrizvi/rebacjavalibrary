package ca.ucalgary.ispia.rebac.util;


/**
 * An cache interface for the model checker
 * @author Mona
 *
 */
public interface Cache <K,V> {
		
	/**
	 * put the pairs of (key, value) into the cache
	 * @param key
	 * @param value
	 */
	public void put (K key, V value);
	
	/**
	 * Retrieves the boolean value of the corresponding key in the cache.
	 * We assume that chech_existance method has been called before calling 
	 * the retrieve method.
	 * @param key : The key we need to obtain its value
	 * @return : boolean value of the triple in the cache
	 */
	public V retrieve (K key);
	
	/**
	 * clears the entire entries of the cache
	 */
	public void clear ();
	
	/**
	 * Checks if an entry (in the form of a key) exists is the cache or not
	 * @param key : the input key
	 * @return : true if the key exists in the cache and false if it does not exist.
	 */
	public boolean check_existance(K key);
	
	/**
	 * For getting the size of the cache
	 * @return : returns the size of the cache
	 */
	public int get_size() ;
	
}
