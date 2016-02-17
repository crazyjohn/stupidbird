package com.stupidbird.db.kv;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class RedisClient implements KVClient {
	JedisPool pool;

	public RedisClient(JedisPool pool) {
		this.pool = pool;
	}

	protected Jedis client() {
		return pool.getResource();
	}

	@Override
	public byte[] get(String key) {
		return client().get(bytes(key));
	}

	@Override
	public void set(String key, byte[] value) {
		client().set(bytes(key), value);
	}

	@Override
	public void del(String key) {
		client().del(key);
	}

	@Override
	public boolean ping() throws Exception {
		return client().ping().equals("");
	}

	@Override
	public void setString(String key, String value) {
		client().set(key, value);
	}

	@Override
	public String getString(String key) {
		return client().get(key);
	}

}
