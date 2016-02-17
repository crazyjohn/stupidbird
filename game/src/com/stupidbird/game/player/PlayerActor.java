package com.stupidbird.game.player;

import com.stupidbird.core.actor.BaseActor;
import com.stupidbird.core.actor.event.ActorEvent;
import com.stupidbird.core.concurrent.NodeExecutor;

/**
 * 玩家actor;
 * 
 * @author crazyjohn
 *
 */
public class PlayerActor extends BaseActor {
	Player player;

	public PlayerActor(NodeExecutor executor, Player player) {
		super(executor);
		this.player = player;
	}

	@Override
	protected void onEvent(ActorEvent event) {
		player.onEvent(event);
	}

	@Override
	protected Object hostedObject() {
		return player;
	}

}
