package com.stupidbird.db.uuid;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.stupidbird.core.annotation.concurrency.GuardedByUnit;
import com.stupidbird.core.annotation.concurrency.ThreadSafeUnit;
import com.stupidbird.db.entity.EntityDBService;

/**
 * 默认的UUID实现;
 * <p>
 * 实现要依赖于去db查询最大的id值，所以要根据具体的db类型来进行查询最大值的优化。(比如为查询的具体id字段创建索引等)
 * 
 * @author crazyjohn
 *
 */
@ThreadSafeUnit
public class GameEntityIdService implements UUIDGenerator {
	protected EntityDBService dbService;
	@GuardedByUnit(whoCareMe = "ConcurrentHashMap")
	private Map<Class<?>, UUID> uuids;

	public static UUIDGenerator buildUUIDService(EntityDBService dbService, Class<?>[] types, int regionId, int serverId)
			throws Exception {
		UUIDGenerator result = new GameEntityIdService(dbService, types, regionId, serverId);
		return result;
	}

	private GameEntityIdService(EntityDBService dbService, Class<?>[] types, int regionId, int serverId)
			throws Exception {
		this.dbService = dbService;
		uuids = new ConcurrentHashMap<Class<?>, UUID>();
		for (Class<?> entityClass : types) {
			// query max id from db
			long maxId = dbService.getMaxEntityId(entityClass);
			uuids.put(entityClass, UUID64.buildDefaultUUID(regionId, serverId, maxId));
		}

	}

	@Override
	public long getNextId(Class<?> classType) {
		UUID uuid = this.uuids.get(classType);
		return uuid.getNextId();
	}
}
