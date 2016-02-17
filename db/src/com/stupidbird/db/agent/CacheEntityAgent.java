package com.stupidbird.db.agent;

import com.stupidbird.db.entity.Entity;

/**
 * 实体缓存代理;
 * 
 * @author crazyjohn
 *
 * @param <T>
 */
public interface CacheEntityAgent<T extends Entity> {

	public String getFrom(String key);

	public void setTo(String key, String value);
	
	public void setCacheKeyGenerator(CacheKeyGenerator generator);
}
