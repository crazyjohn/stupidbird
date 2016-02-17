package com.stupidbird.core.collection;

import java.util.LinkedHashMap;

/**
 * LRU cache;
 * 
 * @author crazyjohn
 *
 * @param <K>
 * @param <V>
 */
@SuppressWarnings("serial")
public class LRUCache<K, V> extends LinkedHashMap<K, V> {
	/** the max cache size */
	private int cacheSize;
	private EvictPolicy<K, V> evictPolicy = new EvictWhenUpToMaxSize<K, V>(this);

	/**
	 * Constructor;
	 * 
	 * @param cacheSize
	 *            the max cache size;
	 * @param evictPolicy
	 *            the evict policy {@link EvictPolicy}
	 */
	public LRUCache(int cacheSize, EvictPolicy<K, V> evictPolicy) {
		this.cacheSize = cacheSize;
		if (evictPolicy != null) {
			this.evictPolicy = evictPolicy;
		}
	}

	/**
	 * Constructor;
	 * 
	 * @param cacheSize
	 *            the max cache size;
	 */
	public LRUCache(int cacheSize) {
		this(cacheSize, null);
	}

	@Override
	protected boolean removeEldestEntry(java.util.Map.Entry<K, V> eldest) {
		return evictPolicy.evict(eldest.getKey(), eldest.getValue());
	}

	/**
	 * Get the cache size;
	 * 
	 * @return
	 */
	public int getCacheSize() {
		return this.cacheSize;
	}
}
