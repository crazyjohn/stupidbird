package com.stupidbird.game.example;

import org.apache.mina.core.session.IoSession;

import com.stupidbird.core.actor.NodeDispatcher;
import com.stupidbird.core.msg.SessionMessage;
import com.stupidbird.core.net.NodeIoHandler;
import com.stupidbird.core.session.GameSession;

public class ServerExampleIoHandler extends NodeIoHandler<GameSession> {

	public ServerExampleIoHandler(NodeDispatcher dispatcher) {
		super(dispatcher);
	}

	@Override
	protected SessionMessage<GameSession> createSessionOpenMessage(GameSession sessionInfo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected GameSession createSessionInfo(IoSession session) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected SessionMessage<GameSession> createSessionCloseMessage(GameSession sessionInfo) {
		// TODO Auto-generated method stub
		return null;
	}

}
