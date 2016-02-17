package com.stupidbird.core.collection;

/**
 * Evict the cache item, when the caches up to the max size;
 * 
 * @author crazyjohn
 *
 * @param <K>
 * @param <V>
 */
public class EvictWhenUpToMaxSize<K, V> implements EvictPolicy<K, V> {
	private LRUCache<K, V> host;

	public EvictWhenUpToMaxSize(LRUCache<K, V> host) {
		this.host = host;
	}

	@Override
	public boolean evict(K key, V value) {
		return host.size() > host.getCacheSize();
	}

}
