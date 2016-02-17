package com.stupidbird.game.msg;

import com.stupidbird.core.msg.Internal;
import com.stupidbird.game.human.Human;
import com.stupidbird.game.io.PlayerSession;
import com.stupidbird.game.player.Player;
import com.stupidbird.game.world.GameWorld;

public class Close extends Internal<PlayerSession> {

	@Override
	public void execute() {
		logger.info(String.format("Session closed: %s", this.session));
		Player player = this.session.object();
		if (player == null) {
			return;
		}
		// 离开游戏世界
		player.globalInjector(GameWorld.class).exit(player);
		Human human = player.getHuman();
		if (human == null) {
			return;
		}
		// 角色登出
		human.onLogout();
	}
}
