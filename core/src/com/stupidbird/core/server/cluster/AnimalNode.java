package com.stupidbird.core.server.cluster;

import java.io.IOException;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;

import com.stupidbird.core.server.Commander;
import com.stupidbird.core.util.GsonUtil;

/**
 * 使用zookeeper作为注册表的节点，故命名为animal
 * 
 * @author crazyjohn
 *
 */
public class AnimalNode extends Commander implements Clusterable {
	ZooKeeper zk;
	private String root;

	public AnimalNode(String root, String registryAddress) throws IOException {
		zk = new ZooKeeper(registryAddress, 2000, new LoggerWatcher());
		this.root = root;
	}

	@Override
	public void registerMe() throws Exception {
		if (zk.exists(childPath(), true) == null) {
			zk.create(childPath(), childData(), Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
		}
	}

	@Override
	public void startup() throws Exception {
		registerMe();
		super.startup();
	}

	private byte[] childData() {
		NodeInfo info = new NodeInfo(defaultConfig.getId(), defaultConfig.getBindIp(), defaultConfig.getPort());
		return GsonUtil.toJson(info).getBytes();
	}

	private String childPath() {
		return root + "/" + defaultConfig.getId();
	}

}
