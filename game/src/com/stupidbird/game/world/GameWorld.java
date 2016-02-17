package com.stupidbird.game.world;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;
import com.stupidbird.core.annotation.concurrency.ThreadSafeUnit;
import com.stupidbird.core.concurrent.NodeExecutor;
import com.stupidbird.game.event.Save;
import com.stupidbird.game.manager.ManagerActor;
import com.stupidbird.game.player.Player;
import com.stupidbird.game.player.PlayerActor;

/**
 * 游戏世界;
 * 
 * @author crazyjohn
 *
 */
public class GameWorld extends ManagerActor implements ActorWorld {
	protected Logger logger = LoggerFactory.getLogger("Server");
	/** 玩家列表 */
	private Map<Long, PlayerActor> actors = new ConcurrentHashMap<Long, PlayerActor>();

	@Inject
	public GameWorld(NodeExecutor executor) {
		super(executor);
	}

	@ThreadSafeUnit
	@Override
	public PlayerActor query(long playerId) {
		return actors.get(playerId);
	}

	@ThreadSafeUnit
	@Override
	public void enter(Player player) {
		actors.put(player.getId(), new PlayerActor(executor, player));
	}

	@ThreadSafeUnit
	@Override
	public void exit(Player player) {
		actors.remove(player.getId());
	}
	
	@Override
	public void tick() {
		for (PlayerActor player : actors.values()) {
			player.tell(new Save());
		}
	}

}
