package com.stupidbird.game.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.stupidbird.core.config.ConfigUtil;
import com.stupidbird.core.os.GameMonitor;
import com.stupidbird.core.server.Node;
import com.stupidbird.core.server.cluster.AnimalNode;
import com.stupidbird.game.GameServerConfig;
import com.stupidbird.game.GlobalModules;
import com.stupidbird.game.io.GameIoHandler;

/**
 * 游戏服;
 * 
 * @author crazyjohn
 *
 */
public class GameServerApp {
	static Logger logger = LoggerFactory.getLogger("Server");

	/**
	 * 入口
	 * 
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) {
		try {
			GameServerConfig config = ConfigUtil.loadConfig(GameServerConfig.class, "game_server.cfg.js");
			Node node = new AnimalNode(config.getRoot(), config.getRegistryAddress());
			// 加载配置
			node.configure(config);
			// 全局模块初始化
			Injector globalInjector = Guice.createInjector(new GlobalModules(config, node));
			// io处理器
			GameIoHandler ioHandler = globalInjector.getInstance(GameIoHandler.class);
			node.setIoHandler(ioHandler);
			// 设置注入器
			ioHandler.setGlobalInjector(globalInjector);
			// 启动
			node.startup();
			// dump
			logger.info("Dump executor info: " + node.executor().dump());
		} catch (Exception e) {
			GameMonitor.catchException(e);
			System.exit(0);
		}
	}

}
