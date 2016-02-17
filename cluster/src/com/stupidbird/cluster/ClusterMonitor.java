package com.stupidbird.cluster;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.ZooKeeper;

import com.stupidbird.core.server.cluster.LoggerWatcher;

public class ClusterMonitor extends LoggerWatcher {
	protected ZooKeeper zk;
	protected String monitorPath;

	public ClusterMonitor(String monitorPath) {
		this.monitorPath = monitorPath;
	}

	public void setZookeeper(ZooKeeper zk) {
		this.zk = zk;
	}

	@Override
	public void process(WatchedEvent event) {
		try {
			super.process(event);
			if (zk.exists(monitorPath, true) != null) {
				logger.info("Root dir list: ");
				for (String childPath : zk.getChildren(monitorPath, this)) {
					logger.info(String.format("Child path: %s", toChildPath(childPath)));
				}
			}
		} catch (KeeperException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private String toChildPath(String childPath) {
		return monitorPath + "/" + childPath;
	}
}
