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
package ca.ucalgary.ispia.rebac.util;


/**
 * A cache interface for the model checker
 * @author Mona
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
