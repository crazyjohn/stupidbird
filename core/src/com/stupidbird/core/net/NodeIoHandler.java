package com.stupidbird.core.net;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.stupidbird.core.actor.NodeDispatcher;
import com.stupidbird.core.msg.SessionMessage;
import com.stupidbird.core.session.GameSession;

/**
 * Base io handler;
 * 
 * @author crazyjohn
 *
 * @param <S>
 */
public abstract class NodeIoHandler<S extends GameSession> extends IoHandlerAdapter {
	private static final String SESSION_INFO = "SESSION_INFO";
	protected Logger logger = LoggerFactory.getLogger(NodeIoHandler.class);
	/** 分发器 */
	protected NodeDispatcher dispatcher;

	public NodeIoHandler(NodeDispatcher dispatcher) {
		this.dispatcher = dispatcher;
	}

	@Override
	public void messageReceived(IoSession session, Object message) throws Exception {
		@SuppressWarnings("unchecked")
		S sessionInfo = (S) session.getAttribute(SESSION_INFO);
		if (sessionInfo == null) {
			// just close
			session.close(true);
		}
		if (message instanceof SessionMessage) {
			@SuppressWarnings("unchecked")
			SessionMessage<S> msg = (SessionMessage<S>) message;
			msg.setSession(sessionInfo);
			dispatcher.dispatch(msg);
		}
	}

	@Override
	public void sessionClosed(IoSession session) throws Exception {
		@SuppressWarnings("unchecked")
		S sessionInfo = (S) session.getAttribute(SESSION_INFO);
		if (sessionInfo != null) {
			session.setAttribute(SESSION_INFO, null);
		}
		SessionMessage<S> sessionCloseMessage = createSessionCloseMessage(sessionInfo);
		if (sessionCloseMessage == null) {
			return;
		}
		dispatcher.dispatch(sessionCloseMessage);
	}

	@Override
	public void sessionOpened(IoSession session) throws Exception {
		S sessionInfo = createSessionInfo(session);
		session.setAttributeIfAbsent(SESSION_INFO, sessionInfo);
		SessionMessage<S> sessionOpenMessage = createSessionOpenMessage(sessionInfo);
		if (sessionOpenMessage == null) {
			return;
		}
		dispatcher.dispatch(sessionOpenMessage);
	}

	@Override
	public void exceptionCaught(IoSession session, Throwable cause) throws Exception {
		// close session
		logger.error(String.format("Exception caught, session: %s", session), cause);
		session.close(true);
	}

	/**
	 * Create a session open message, when received mina session open event;
	 * 
	 * @param sessionInfo
	 * @return
	 */
	protected abstract SessionMessage<S> createSessionOpenMessage(S sessionInfo);

	/**
	 * Create the session info;
	 * 
	 * @param session
	 * @return
	 */
	protected abstract S createSessionInfo(IoSession session);

	/**
	 * Create a session close message, when received mina session close event;
	 * 
	 * @param sessionInfo
	 * @return
	 */
	protected abstract SessionMessage<S> createSessionCloseMessage(S sessionInfo);

}
