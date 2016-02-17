package com.stupidbird.game.msg;

import com.stupidbird.core.msg.Internal;
import com.stupidbird.game.io.PlayerSession;

public class Open extends Internal<PlayerSession> {

	@Override
	public void execute() {
		logger.info(String.format("Session opened: %s", this.session));
	}

}
