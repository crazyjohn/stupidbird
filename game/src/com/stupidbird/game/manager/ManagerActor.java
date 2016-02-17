package com.stupidbird.game.manager;

import com.stupidbird.core.actor.BaseActor;
import com.stupidbird.core.concurrent.NodeExecutor;
import com.stupidbird.core.server.Tickable;

/**
 * 全局管理器级别的actor;
 * 
 * @author crazyjohn
 *
 */
public abstract class ManagerActor extends BaseActor implements Tickable {

	public ManagerActor(NodeExecutor executor) {
		super(executor);
	}

	@Override
	public void tick() {
		// logger.info(String.format("%s: GameWorld tick...",
		// Thread.currentThread()));
	}
}
