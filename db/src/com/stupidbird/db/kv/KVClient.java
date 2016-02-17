package com.stupidbird.db.kv;

import java.nio.charset.Charset;

public interface KVClient {
	/** 默认编码 */
	Charset DEFAULT_CHARSET = Charset.forName("UTF-8");

	default String string(byte[] bytes) {
		return new String(bytes, DEFAULT_CHARSET);
	}

	default byte[] bytes(String key) {
		return key.getBytes(DEFAULT_CHARSET);
	}

	public void set(String key, byte[] value);

	public byte[] get(String key);

	public void setString(String key, String value);

	public String getString(String key);

	public void del(String key);

	public boolean ping() throws Exception;
}
