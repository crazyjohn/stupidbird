package com.stupidbird.game.example;

import com.stupidbird.core.server.Node;
import com.stupidbird.core.server.ServerNode;

public class ServerApp {

	public static void main(String[] args) throws Exception {
		Node node = new ServerNode();
		node.setIoHandler(new ServerExampleIoHandler(node));
		node.startup();
	}

}
