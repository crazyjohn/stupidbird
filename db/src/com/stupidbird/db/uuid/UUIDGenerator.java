package com.stupidbird.db.uuid;

/**
 * UUID生成器接口;
 * 
 * @author crazyjohn
 *
 */
public interface UUIDGenerator {

	public long getNextId(Class<?> classType);
}
