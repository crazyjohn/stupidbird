package com.stupidbird.db.cassandra;

import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Statement;
import com.stupidbird.db.DBService;

public interface CassandraDBService extends DBService {
	/**
	 * Execute the cassandra query language;
	 * 
	 * @param cql
	 */
	public ResultSet executeCQL(String cql);

	/**
	 * Execute the statement;
	 * 
	 * @param statement
	 * @return
	 */
	public ResultSet executeStatement(Statement statement);

	/**
	 * Generate the prepare statement;
	 * 
	 * @param prepareSql
	 * @return
	 */
	public PreparedStatement prepare(String prepareSql);
}
