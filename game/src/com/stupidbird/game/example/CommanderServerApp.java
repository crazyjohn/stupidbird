package com.stupidbird.game.example;

import com.stupidbird.core.server.Commander;

public class CommanderServerApp {

	public static void main(String[] args) throws Exception {
		Commander commander = new Commander();
		commander.setIoHandler(new ServerExampleIoHandler(commander));
		commander.startup();
	}

}
