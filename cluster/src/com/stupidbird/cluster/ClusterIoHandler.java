package com.stupidbird.cluster;

import org.apache.mina.core.session.IoSession;

import com.stupidbird.core.actor.NodeDispatcher;
import com.stupidbird.core.msg.SessionMessage;
import com.stupidbird.core.net.NodeIoHandler;

public class ClusterIoHandler extends NodeIoHandler<NodeSession> {

	public ClusterIoHandler(NodeDispatcher dispatcher) {
		super(dispatcher);
	}

	@Override
	protected SessionMessage<NodeSession> createSessionOpenMessage(NodeSession sessionInfo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected NodeSession createSessionInfo(IoSession session) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected SessionMessage<NodeSession> createSessionCloseMessage(NodeSession sessionInfo) {
		// TODO Auto-generated method stub
		return null;
	}

}
