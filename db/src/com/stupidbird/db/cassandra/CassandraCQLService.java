package com.stupidbird.db.cassandra;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.Statement;

/**
 * Cassandra的DB服务;
 * <p>
 * FIXME: crazyjohn 后期添加并发支持
 * 
 * @author crazyjohn
 *
 */
public class CassandraCQLService implements CassandraDBService {
	private Cluster cluster;
	protected Session session;

	public CassandraCQLService(String host, int port, String database) {
		cluster = Cluster.builder().addContactPoint(host).withPort(port).build();
		session = cluster.connect(database);
	}

	@Override
	public ResultSet executeCQL(String cql) {
		return session.execute(cql);
	}

	@Override
	public ResultSet executeStatement(Statement statement) {
		return session.execute(statement);
	}

	@Override
	public PreparedStatement prepare(String prepareSql) {
		return session.prepare(prepareSql);
	}

	@Override
	public void shutdown() {
		this.session.close();
		this.cluster.close();
	}

	@Override
	public void startup() {
		// TODO Auto-generated method stub

	}

}
