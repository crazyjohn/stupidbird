package com.stupidbird.core.server.cluster;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggerWatcher implements Watcher {
	protected Logger logger = LoggerFactory.getLogger("Server");

	@Override
	public void process(WatchedEvent event) {
		logger.info(String.format("Trigger event: %s", event.getType()));
	}

}
