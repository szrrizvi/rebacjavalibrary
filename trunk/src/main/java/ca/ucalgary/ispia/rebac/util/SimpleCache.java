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

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import ca.ucalgary.ispia.rebac.util.Constants;

/**
 * 
 * @author Mona
 *
 * @param <K>
 * @param <V>
 */
public class SimpleCache<K, V> implements Cache<K, V>{
	
	//overview : implements a simple cache strategy. It has common cache operations but
	//within the put method it first examines whether the cache has reached a particular
	//size or not. If yes, then it randomly deletes (cache.size * alpha) number of entries
	//from the cache.
	
	private Map<K, V> cache;
	
	//Constructor
	public SimpleCache () {
		//effect: initializes this to be empty.
		cache = new HashMap<K, V>();
	}

	@Override
	public void put(K key, V value) {
		/*if (cache.size() > Constants.cache_size)
			clear();*/
		if (cache.size() >= Constants.cache_size) {
			int k = (int) (cache.size() * Constants.alpha);
			int n = cache.size();
			//System.out.println("before: "+cache.size());
			random_cache_clear(k, n);
			//System.out.println("after: "+cache.size());

		}
		cache.put(key, value);
	}

	@Override
	public V retrieve(K key) {
		assert (cache.containsKey(key));
		return cache.get(key);
	}

	@Override
	public void clear() {
		cache.clear();
	}

	@Override
	public boolean check_existance(K key) {
		if (cache.containsKey(key))
			return true;
		else return false;
	}

	@Override
	public int get_size() {
		return cache.size();
	}
	
	/**
	 * Returns the cache 
	 * @return : return cache
	 */
	public Map<K, V> getCache() {
		return cache;
	}

	/**
	 * set the cache
	 * @param cache : the input cache
	 */
	public void setCache(Map<K, V> cache) {
		this.cache = cache;
	}
	

	/**
	 * Selects k entries of n entries in the cache and delete them
	 * assumption: n >= k > 0 
	 * @param k 
	 * @param n
	 */
	public void random_cache_clear (int k, int n) {
		assert (n >= k);
		assert (k > 0);
		
		Iterator<Entry<K,V>> cache_iterator = cache.entrySet().iterator();
		while (cache_iterator.hasNext()) {
		   cache_iterator.next(); 
		   if (probSelection(k, n)){
				cache_iterator.remove();
				n--;
				k--;
			}
		   else {
			    n--;
			}
		}
	}
	
	/**
	 * returns true with k/n probability and false with (n-k)/n probability
	 * assumption : n >= k > 0
	 * @param k
	 * @param n
	 * @return
	 */
	private boolean probSelection (int k, int n) {
		assert (n >= k);
		assert (n >  0);
		double random = Math.random();
		float prob = (float)k/(float)n;
		boolean res = (random < prob ? true : false);
		return res;
	}

}
