package com.stupidbird.game;

import com.stupidbird.core.config.NodeConfig;

/**
 * 游戏服务器配置;
 * 
 * @author crazyjohn
 *
 */
public class GameServerConfig extends NodeConfig {
	private String dbHost;
	private int dbPort;
	private String dbName;
	private int dbThreadCount;
	private String root;
	private String registryAddress;

	public String getDbHost() {
		return dbHost;
	}

	public void setDbHost(String dbHost) {
		this.dbHost = dbHost;
	}

	public int getDbPort() {
		return dbPort;
	}

	public void setDbPort(int dbPort) {
		this.dbPort = dbPort;
	}

	public String getDbName() {
		return dbName;
	}

	public void setDbName(String dbName) {
		this.dbName = dbName;
	}

	public int getDbThreadCount() {
		return dbThreadCount;
	}

	public void setDbThreadCount(int dbThreadCount) {
		this.dbThreadCount = dbThreadCount;
	}

	public String getRoot() {
		return root;
	}

	public void setRoot(String root) {
		this.root = root;
	}

	public String getRegistryAddress() {
		return registryAddress;
	}

	public void setRegistryAddress(String registryAddress) {
		this.registryAddress = registryAddress;
	}

}
