package com.stupidbird.game.io;

import org.apache.mina.core.session.IoSession;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.stupidbird.core.actor.NodeDispatcher;
import com.stupidbird.core.msg.SessionMessage;
import com.stupidbird.core.net.NodeIoHandler;
import com.stupidbird.db.entity.EntityDBService;
import com.stupidbird.db.uuid.UUIDGenerator;
import com.stupidbird.game.msg.Close;
import com.stupidbird.game.msg.Open;

public class GameIoHandler extends NodeIoHandler<PlayerSession> {
	@Inject
	private EntityDBService dbService;
	@Inject
	private UUIDGenerator uuidService;
	private Injector globalInjector;

	@Inject
	public GameIoHandler(NodeDispatcher dispatcher) {
		super(dispatcher);
	}

	@Override
	protected SessionMessage<PlayerSession> createSessionOpenMessage(PlayerSession sessionInfo) {
		Open msg = new Open();
		msg.setSession(sessionInfo);
		return msg;
	}

	@Override
	protected SessionMessage<PlayerSession> createSessionCloseMessage(PlayerSession sessionInfo) {
		Close msg = new Close();
		msg.setSession(sessionInfo);
		return msg;
	}

	@Override
	protected PlayerSession createSessionInfo(IoSession session) {
		PlayerSession sessionInfo = new PlayerSession(session, dbService, uuidService, globalInjector);
		return sessionInfo;
	}

	public void setGlobalInjector(Injector injector) {
		this.globalInjector = injector;
	}

}
