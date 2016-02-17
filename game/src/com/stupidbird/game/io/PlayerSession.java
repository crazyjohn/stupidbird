package com.stupidbird.game.io;

import org.apache.mina.core.session.IoSession;

import com.google.inject.Injector;
import com.stupidbird.core.session.NodeBindableSession;
import com.stupidbird.db.entity.EntityDBService;
import com.stupidbird.db.uuid.UUIDGenerator;
import com.stupidbird.game.player.Player;

/**
 * 玩家会话
 * 
 * @author crazyjohn
 *
 */
public class PlayerSession extends NodeBindableSession<Player> {
	private final Player player;

	public PlayerSession(IoSession session, EntityDBService dbService, UUIDGenerator uuidService,
			Injector globalInjector) {
		super(session);
		// 构建玩家对象
		player = new Player(this, dbService, uuidService, globalInjector);
	}

	@Override
	public Player object() {
		return player;
	}

	@Override
	public String toString() {
		return this.session.toString();
	}

}
