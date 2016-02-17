package com.stupidbird.db.agent;

import java.io.Serializable;

/**
 * 缓存key的生成器;
 * 
 * @author crazyjohn
 *
 */
public interface CacheKeyGenerator {
	public String generate(Class<?> entityClass, Serializable id);
}
