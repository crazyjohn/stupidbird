package com.stupidbird.core.session;

import org.apache.mina.core.session.IoSession;

import com.stupidbird.core.msg.Message;

/**
 * 基础会话实现;
 * 
 * @author crazyjohn
 *
 */
public class BaseSession implements GameSession {
	/** bound session */
	protected IoSession session;

	public BaseSession(IoSession session) {
		this.session = session;
	}

	@Override
	public void close() {
		session.close(true);
	}

	@Override
	public void writeMessage(Message message) {
		if (session != null) {
			session.write(message);
		}
	}

}
