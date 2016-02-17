package com.stupidbird.cluster.app;

import com.stupidbird.cluster.ClusterConfig;
import com.stupidbird.cluster.ClusterIoHandler;
import com.stupidbird.cluster.zoo.ZooRegistry;
import com.stupidbird.core.config.ConfigUtil;

/**
 * 前端代理服务器(eg:agent/login)
 * 
 * @author crazyjohn
 *
 */
public class FrontEndServerApp {

	public static void main(String[] args) {
		try {
			ClusterConfig config = ConfigUtil.loadConfig(ClusterConfig.class, "cluster.cfg.js");
			ZooRegistry nodes = new ZooRegistry(config.getRoot(), config.getRegistryAddress(), 2000);
			nodes.configure(config);
			nodes.setIoHandler(new ClusterIoHandler(nodes));
			nodes.startup();
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
		}
	}

}
