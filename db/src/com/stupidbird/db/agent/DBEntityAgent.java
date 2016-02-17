package com.stupidbird.db.agent;

import java.io.Serializable;
import java.util.List;

import com.stupidbird.core.os.GameMonitor;
import com.stupidbird.core.util.GsonUtil;
import com.stupidbird.db.entity.Entity;
import com.stupidbird.db.entity.EntityDBService;
import com.stupidbird.db.kv.KVClient;
import com.stupidbird.db.kv.LocalCache;

/**
 * 数据实体代理入口;
 * <P>
 * FIXME: crazyjohn 1查询走缓存获取2实体分类维护
 * 
 * @author crazyjohn
 *
 */
public class DBEntityAgent implements CacheEntityAgent<Entity>, EntityDBService {
	protected KVClient cacheClient;
	protected EntityDBService dbService;
	protected CacheKeyGenerator generator = (Class<?> entityClass, Serializable id) -> {
		return entityClass.getSimpleName() + "#" + id;
	};

	public DBEntityAgent(KVClient cacheClient, EntityDBService dbService) {
		this.cacheClient = cacheClient;
		this.dbService = dbService;
	}

	public DBEntityAgent(EntityDBService dbService) {
		this(new LocalCache(), dbService);
	}

	@Override
	public String getFrom(String key) {
		return cacheClient.getString(key);
	}

	@Override
	public void setTo(String key, String value) {
		cacheClient.setString(key, value);
	}

	@Override
	public void insert(Entity entity) {
		this.update(entity);
	}

	@Override
	public void delete(Entity entity) {
		// 缓存中删除
		this.cacheClient.del(generateCacheKey(entity.getClass(), entity.getId()));
		this.dbService.delete(entity);
	}

	@Override
	public void update(Entity entity) {
		// 更新缓存
		this.cacheClient.setString(generateCacheKey(entity.getClass(), entity.getId()), GsonUtil.toJson(entity));
		// 更新到db
		this.dbService.update(entity);
	}

	@Override
	public <T extends Entity> T get(Class<T> entityClass, Serializable id) {
		// 先从缓存中加载
		String json = this.getFrom(generateCacheKey(entityClass, id));
		T entity = null;
		if (json != null) {
			try {
				entity = GsonUtil.fromJson(json, entityClass);
			} catch (Exception e) {
				GameMonitor.catchException(e);
			}
		}
		if (entity != null) {
			return entity;
		}
		// 从数据库加载
		entity = dbService.get(entityClass, id);
		// 放入缓存
		this.cacheClient.setString(this.generateCacheKey(entityClass, id), GsonUtil.toJson(entity));
		return entity;
	}

	@Override
	public <T> List<T> query(Class<T> entityClass, String[] params, Object[] values) {
		return this.dbService.query(entityClass, params, values);
	}

	@Override
	public long getMaxEntityId(Class<?> entityClass) throws Exception {
		return this.dbService.getMaxEntityId(entityClass);
	}

	@Override
	public void startup() throws Exception {
		this.dbService.startup();
	}

	@Override
	public void shutdown() throws InterruptedException {
		this.dbService.shutdown();
	}

	/**
	 * 根据实体类型和id生成缓存key
	 * 
	 * @param entityClass
	 * @param id
	 * @return
	 */
	protected String generateCacheKey(Class<?> entityClass, Serializable id) {
		// 格式：game#platform#serverId#entityName#id
		return generator.generate(entityClass, id);
	}

	@Override
	public void setCacheKeyGenerator(CacheKeyGenerator generator) {
		this.generator = generator;
	}

}
