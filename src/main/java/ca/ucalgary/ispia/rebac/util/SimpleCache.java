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
