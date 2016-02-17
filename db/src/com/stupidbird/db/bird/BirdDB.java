package com.stupidbird.db.bird;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.stupidbird.core.concurrent.NodeExecutor;
import com.stupidbird.core.concurrent.MultiThreadExecutor;
import com.stupidbird.core.util.GsonUtil;
import com.stupidbird.db.entity.Entity;

/**
 * 这算个鸟DB啊<br>
 * FIXME: crazyjohn 异步把记录写入文件：1=oplog + async write;2=rdb
 * 
 * @author crazyjohn
 *
 */
public class BirdDB {
	protected Map<Class<?>, Table> tables = new ConcurrentHashMap<Class<?>, Table>();
	NodeExecutor executor;

	public BirdDB(int nThreads) {
		executor = new MultiThreadExecutor("BirdDBExecutor", nThreads);
	}

	public BirdDB() {
		// single thread
		this(1);
	}

	public void delete(Entity entity) {
		Table table = table(entity.getClass());
		table.delete(entity.getId());
	}

	private Table table(Class<?> tableClass) {
		Table table = tables.get(tableClass);
		if (table == null) {
			table = new BirdTable(tableClass);
			tables.put(tableClass, table);
		}
		return table;
	}

	public void update(Entity entity) {
		Table table = table(entity.getClass());
		table.update(entity.getId(), GsonUtil.toJson(entity));
	}

	@SuppressWarnings("unchecked")
	public <T> T get(Class<?> entityClass, Serializable id) {
		Table table = table(entityClass);
		return (T) table.get(id);
	}

	public void insert(Entity entity) {
		Table table = table(entity.getClass());
		table.insert(entity.getId(), GsonUtil.toJson(entity));
	}

	public long getMaxEntityId(Class<?> entityClass) {
		Table table = table(entityClass);
		return table.maxId();
	}

	public <T> List<T> query(Class<T> entityClass, String[] params, Object[] values) {
		Table table = table(entityClass);
		return table.query(params, values);
	}

	public <T> List<T> query(Class<T> entityClass, int from, int to) {
		Table table = table(entityClass);
		return table.limit(from, to);
	}

	public void startup() {
		// TODO Auto-generated method stub

	}

	public void shutdown() {
		// TODO Auto-generated method stub

	}

}
