package com.stupidbird.db.cassandra;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Statement;
import com.datastax.driver.core.querybuilder.QueryBuilder;
import com.datastax.driver.core.querybuilder.Select;
import com.datastax.driver.core.querybuilder.Update;
import com.stupidbird.db.entity.Entity;
import com.stupidbird.db.entity.EntityAssembler;
import com.stupidbird.db.entity.EntityDBService;

/**
 * Cassandra实体数据会话服务;
 * 
 * @author crazyjohn
 *
 */
public class CassandraEntityService implements EntityDBService {
	/** CQL级别的数据服务 */
	protected CassandraDBService cqlDBService;

	public CassandraEntityService(String host, int port, String database) {
		this.cqlDBService = new CassandraCQLService(host, port, database);
	}

	@Override
	public void insert(Entity entity) {
		cqlDBService.executeStatement(toInsertStatement(entity));
	}

	@Override
	public void delete(Entity entity) {
		cqlDBService.executeStatement(toDeleteStatement(entity));
	}

	@Override
	public void update(Entity entity) {
		cqlDBService.executeStatement(toUpdateStatement(entity));
	}

	@Override
	public void startup() throws Exception {
		cqlDBService.startup();
	}

	@Override
	public void shutdown() throws InterruptedException {
		cqlDBService.shutdown();
	}

	protected Statement toInsertStatement(Entity entity) {
		Statement insert = QueryBuilder.insertInto(entity.getTableName()).values(entity.params(), entity.values());
		return insert;
	}

	protected Statement toDeleteStatement(Entity entity) {
		Statement delete = QueryBuilder.delete().from(entity.getTableName())
				.where(QueryBuilder.eq(entity.getIdName(), entity.getId()));
		return delete;
	}

	protected Statement toUpdateStatement(Entity entity) {
		Update update = QueryBuilder.update(entity.getTableName());
		Object[] values = entity.values();
		// 全属性更新
		int index = 0;
		for (String eachParam : entity.params()) {
			update.with(QueryBuilder.set(eachParam, values[index]));
			index++;
		}
		update.where(QueryBuilder.eq(entity.getIdName(), entity.getId()));
		return update;
	}

	protected Statement toQueryCQL(String queryName, String[] params, Object[] values) {
		Select select = QueryBuilder.select().from(queryName);
		int index = 0;
		for (String param : params) {
			select.where(QueryBuilder.eq(param, values[index]));
			index++;
		}
		return select;
	}

	protected <T> List<T> query(String xql, EntityAssembler<T> assembler) {
		List<T> result = new ArrayList<T>();
		ResultSet resultSet = this.cqlDBService.executeCQL(xql);
		for (Row row : resultSet) {
			T t = assembler.assemble(row);
			result.add(t);
		}
		return result;
	}

	@Override
	public <T extends Entity> T get(Class<T> entityClass, Serializable id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> List<T> query(Class<T> entityClass, String[] params, Object[] values) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getMaxEntityId(Class<?> entityClass) {
		// TODO Auto-generated method stub
		return 0;
	}

	// protected EntityAssembler<HumanEntity> humanEntityAssembler = (row) -> {
	// HumanEntity entity = new HumanEntity();
	// entity.setId(row.getInt(0));
	// entity.setPlayerId(row.getInt(1));
	// entity.setName(row.getString(2));
	// entity.setLevel(row.getInt(3));
	// return entity;
	// };

	// cassandra way
	// List<HumanEntity> entities = dbService().query(
	// String.format("SELECT * FROM human WHERE id = %d",
	// selectRole.getRoleId()), humanEntityAssembler);

	// cassandra way
	// List<HumanEntity> entities = dbService().query(
	// String.format("SELECT * FROM human where playerId = %d",
	// this.player.getId()), humanEntityAssembler);

	// cassandra way
	// List<PlayerEntity> entities = player.getDBService().query(
	// String.format("SELECT * FROM player WHERE puid = '%s'",
	// login.getPuid()), (row) -> {
	// PlayerEntity entity = new PlayerEntity();
	// entity.setId(row.getInt(0));
	// entity.setPuid(row.getString(1));
	// return entity;
	// });

}
