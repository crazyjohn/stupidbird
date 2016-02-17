package com.stupidbird.game.player.module;

import com.stupidbird.core.msg.Message;
import com.stupidbird.db.entity.EntityDBService;
import com.stupidbird.game.player.Player;

public abstract class BasePlayerModule implements PlayerModule {
	protected Player player;
	protected boolean enabled;

	public BasePlayerModule(Player player) {
		this.player = player;
	}

	@Override
	public void enabled(boolean flag) {
		this.enabled = flag;
	}

	@Override
	public void onMessage(Message msg) throws Exception {
		// TODO Auto-generated method stub
	}

	protected EntityDBService dbService() {
		return player.getDBService();
	}
}
