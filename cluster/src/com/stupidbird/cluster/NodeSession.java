package com.stupidbird.cluster;

import org.apache.mina.core.session.IoSession;

import com.stupidbird.core.session.BaseSession;

public class NodeSession extends BaseSession {

	public NodeSession(IoSession session) {
		super(session);

	}

}
