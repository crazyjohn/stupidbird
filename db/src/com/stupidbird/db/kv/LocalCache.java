package com.stupidbird.db.kv;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class LocalCache implements KVClient {
	private Map<String, byte[]> cacheBytes = new ConcurrentHashMap<String, byte[]>();
	private Map<String, String> cacheString = new ConcurrentHashMap<String, String>();

	@Override
	public byte[] get(String key) {
		return cacheBytes.get(key);
	}

	@Override
	public void set(String key, byte[] value) {
		this.cacheBytes.put(key, value);
	}

	@Override
	public void del(String key) {
		this.cacheBytes.remove(key);
	}

	@Override
	public boolean ping() throws Exception {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setString(String key, String value) {
		this.cacheString.put(key, value);
	}

	@Override
	public String getString(String key) {
		return this.cacheString.get(key);
	}

}
