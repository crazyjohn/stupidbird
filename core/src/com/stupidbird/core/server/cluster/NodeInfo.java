package com.stupidbird.core.server.cluster;

import com.stupidbird.core.router.Routee;


public class NodeInfo implements Routee {
	private final int id;
	private final String ip;
	private final int port;

	public NodeInfo(int id, String ip, int port) {
		this.id = id;
		this.ip = ip;
		this.port = port;
	}

	public int getId() {
		return id;
	}

	public String getIp() {
		return ip;
	}

	public int getPort() {
		return port;
	}

}
