package com.stupidbird.core.config;

/**
 * The base server config;
 * 
 * @author crazyjohn
 *
 */
public class NodeConfig implements Config {
	private int id;
	private String name;
	private String bindIp = "0.0.0.0";
	private int port = 8001;
	private String telnetIp = "0.0.0.0";
	private int telnetPort = 7001;
	private int msgThreadCount = 1;

	public String getTelnetIp() {
		return telnetIp;
	}

	public void setTelnetIp(String telnetIp) {
		this.telnetIp = telnetIp;
	}

	public int getTelnetPort() {
		return telnetPort;
	}

	public void setTelnetPort(int telnetPort) {
		this.telnetPort = telnetPort;
	}

	public String getBindIp() {
		return bindIp;
	}

	public void setBindIp(String bindIp) {
		this.bindIp = bindIp;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	@Override
	public void validate() {
		// TODO do nothing ??

	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getMsgThreadCount() {
		return msgThreadCount;
	}

	public void setMsgThreadCount(int msgThreadCount) {
		this.msgThreadCount = msgThreadCount;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

}
