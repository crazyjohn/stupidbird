package com.stupidbird.cluster.zoo;

import java.io.IOException;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.stupidbird.cluster.ClusterMonitor;
import com.stupidbird.cluster.Registry;
import com.stupidbird.core.server.Commander;
import com.stupidbird.core.server.cluster.NodeInfo;
import com.stupidbird.core.util.GsonUtil;

/**
 * 使用zookeeper实现的注册表
 * 
 * @author crazyjohn
 *
 */
public class ZooRegistry extends Commander implements Registry {
	Logger logger = LoggerFactory.getLogger("Server");
	ZooKeeper zk;
	String root = "/nodes";

	public ZooRegistry(String root, String address, int timeout) throws IOException {
		ClusterMonitor monitor = new ClusterMonitor(root);
		zk = new ZooKeeper(address, timeout, monitor);
		monitor.setZookeeper(zk);
		this.root = root;
	}

	@Override
	public NodeInfo query(int id) throws Exception {
		NodeInfo info = GsonUtil.fromJson(new String(zk.getData(toPath(id), true, null)), NodeInfo.class);
		return info;
	}

	@Override
	public void register(NodeInfo info) throws Exception {
		if (zk.exists(nodePath(info), true) != null) {
			return;
		}
		zk.create(nodePath(info), nodeData(info), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
	}

	private byte[] nodeData(NodeInfo info) {
		return GsonUtil.toJson(info).getBytes();
	}

	private String nodePath(NodeInfo info) {
		return toPath(info.getId());
	}

	private String toPath(int id) {
		return root + "/" + id;
	}

	@Override
	public NodeInfo select() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void shutdown() throws InterruptedException {
		zk.close();
		super.shutdown();
	}

	@Override
	public void startup() throws Exception {
		if (zk.exists(root, true) == null) {
			zk.create(root, "rootData".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
		}
		logger.info(String.format("Root data: %s", new String(zk.getData(root, true, null))));
		logger.info(String.format("Root dir list: %s", zk.getChildren(root, true)));
		super.startup();
	}

}
