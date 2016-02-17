package com.stupidbird.cluster;

import com.stupidbird.core.config.NodeConfig;

public class ClusterConfig extends NodeConfig {
	private String root;
	private String registryAddress;

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
