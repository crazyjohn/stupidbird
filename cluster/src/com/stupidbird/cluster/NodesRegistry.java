package com.stupidbird.cluster;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.stupidbird.core.router.RoundRobinRouter;
import com.stupidbird.core.router.Router;
import com.stupidbird.core.server.Commander;
import com.stupidbird.core.server.cluster.NodeInfo;

public class NodesRegistry extends Commander implements Registry {
	private Map<Integer, NodeInfo> nodes = new ConcurrentHashMap<Integer, NodeInfo>();
	private Router router;

	public NodesRegistry(Router router) {
		this.router = router;
	}
	
	public NodesRegistry() {
		this(new RoundRobinRouter());
	}

	@Override
	public NodeInfo query(int id) {
		return nodes.get(id);
	}

	@Override
	public void register(NodeInfo info) {
		nodes.put(info.getId(), info);
		router.add(info);
	}

	@Override
	public NodeInfo select() {
		return router.select();
	}

}
