package com.stupidbird.db.mongo;

import java.io.Serializable;
import java.net.UnknownHostException;
import java.util.List;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import org.mongodb.morphia.query.Query;

import com.mongodb.DB;
import com.mongodb.MongoClient;
import com.stupidbird.db.entity.Entity;
import com.stupidbird.db.entity.EntityDBService;

/**
 * The mongo db service;
 * 
 * @author crazyjohn
 *
 */
public class MongoEntityService implements EntityDBService {
	/** mongo instance */
	MongoClient mongo;
	/** db instance */
	DB db;
	// String dbName = "stupidbird";
	Datastore dataStore;

	public MongoEntityService(String host, int port, String dbName) throws UnknownHostException {
		mongo = new MongoClient(host, port);
		db = mongo.getDB(dbName);
		dataStore = new Morphia().createDatastore(mongo, dbName);
	}

	@Override
	public void update(Entity entity) {
		this.dataStore.save(entity);
	}

	@Override
	public void startup() {
		// TODO Auto-generated method stub

	}

	@Override
	public void shutdown() throws InterruptedException {
		this.mongo.close();
	}

	@Override
	public void insert(Entity entity) {
		this.dataStore.save(entity);
	}

	@Override
	public void delete(Entity entity) {
		this.dataStore.delete(entity);
	}

	@Override
	public <T extends Entity> T get(Class<T> entityClass, Serializable id) {
		Query<T> query = this.dataStore.createQuery(entityClass).field(getEntityIdName(entityClass)).equal(id);
		return query.asList().get(0);
	}

	protected <T> String getEntityIdName(Class<T> entityClass) {
		// FIXME: crazyjohn
		return "";
	}

	@Override
	public <T> List<T> query(Class<T> entityClass, String[] params, Object[] values) {
		Query<T> query = this.dataStore.createQuery(entityClass);
		int index = 0;
		for (String eachParam : params) {
			query.field(eachParam).equal(values[index]);
			index++;
		}
		return query.asList();
	}

	@Override
	public long getMaxEntityId(Class<?> entityClass) throws Exception {
		Query<?> query = this.dataStore.createQuery(entityClass).order("-id").limit(1);
		List<?> result = query.asList();
		if (result == null || result.size() == 0) {
			return 0;
		}
		return (long) entityClass.getMethod("getId").invoke(query.asList().get(0));
	}
}
