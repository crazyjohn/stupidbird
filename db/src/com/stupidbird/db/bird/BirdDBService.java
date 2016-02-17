package com.stupidbird.db.bird;

import java.io.Serializable;
import java.util.List;

import com.stupidbird.db.entity.Entity;
import com.stupidbird.db.entity.EntityDBService;

public class BirdDBService implements EntityDBService {
	BirdDB db = new BirdDB();

	@Override
	public void startup() throws Exception {
		this.db.startup();
	}

	@Override
	public void shutdown() throws InterruptedException {
		this.db.shutdown();
	}

	@Override
	public void insert(Entity entity) {
		this.db.insert(entity);
	}

	@Override
	public void delete(Entity entity) {
		this.db.delete(entity);
	}

	@Override
	public void update(Entity entity) {
		this.db.update(entity);
	}

	@Override
	public <T extends Entity> T get(Class<T> entityClass, Serializable id) {
		return this.db.get(entityClass, id);
	}

	@Override
	public <T> List<T> query(Class<T> entityClass, String[] params, Object[] values) {
		return this.db.query(entityClass, params, values);
	}

	@Override
	public long getMaxEntityId(Class<?> entityClass) throws Exception {
		return db.getMaxEntityId(entityClass);
	}

}
