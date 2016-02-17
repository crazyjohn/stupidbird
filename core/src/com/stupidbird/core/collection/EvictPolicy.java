package com.stupidbird.core.collection;

/**
 * The cache evict policy;
 * 
 * @author crazyjohn
 *
 * @param <K>
 * @param <V>
 */
public interface EvictPolicy<K, V> {
	
	public boolean evict(K key, V value);
}
