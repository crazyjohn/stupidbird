package com.stupidbird.game;

import java.net.UnknownHostException;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Scopes;
import com.stupidbird.core.actor.NodeDispatcher;
import com.stupidbird.core.concurrent.NodeExecutor;
import com.stupidbird.core.net.NodeIoHandler;
import com.stupidbird.core.server.Node;
import com.stupidbird.db.agent.DBEntityAgent;
import com.stupidbird.db.bird.BirdDBService;
import com.stupidbird.db.entity.EntityDBService;
import com.stupidbird.db.mongo.ConcurrentMongoDBService;
import com.stupidbird.db.uuid.GameEntityIdService;
import com.stupidbird.db.uuid.UUIDGenerator;
import com.stupidbird.game.entity.HumanEntity;
import com.stupidbird.game.entity.PlayerEntity;
import com.stupidbird.game.io.GameIoHandler;
import com.stupidbird.game.world.GameWorld;

/**
 * 全局的服务器配置;
 * 
 * @author crazyjohn
 *
 */
public class GlobalModules extends AbstractModule {
	Node node;
	GameServerConfig config;

	public GlobalModules(GameServerConfig config, Node node) {
		this.node = node;
		this.config = config;
	}

	@Override
	protected void configure() {
		// io处理器
		bind(NodeIoHandler.class).to(GameIoHandler.class).in(Scopes.SINGLETON);

		// 游戏世界
		GameWorld world = new GameWorld(executor());
		bind(GameWorld.class).toInstance(world);
		// 注册心跳
		node.registerTick(world);
		// 数据服务
		EntityDBService dbService;
		try {
			dbService = buildDBAgent(config);
			bind(EntityDBService.class).toInstance(dbService);
			// 注册管理
			node.registerLifecycle(dbService);
			// UUID服务
			UUIDGenerator uuidService = GameEntityIdService.buildUUIDService(dbService, new Class<?>[] {
					PlayerEntity.class, HumanEntity.class }, 1, 1);
			bind(UUIDGenerator.class).toInstance(uuidService);
		} catch (Exception e) {
			throw new RuntimeException("Configure GlobalModules error", e);
		}
	}

	/**
	 * 构建数据服务
	 * 
	 * @param config
	 * @return
	 * @throws Exception
	 */
	private EntityDBService buildDBAgent(GameServerConfig config) throws Exception {
		EntityDBService dbService = new BirdDBService();
		EntityDBService agent = new DBEntityAgent(dbService);
		return agent;
	}

	protected EntityDBService buildMongoDBService(GameServerConfig config) throws UnknownHostException {
		return new ConcurrentMongoDBService(config.getDbHost(), config.getDbPort(), config.getDbName(),
				config.getDbThreadCount(), new Class<?>[] { PlayerEntity.class, HumanEntity.class });
	}

	@Provides
	private NodeExecutor executor() {
		return node.executor();
	}

	@Provides
	private NodeDispatcher dispatcher() {
		return node;
	}

}
