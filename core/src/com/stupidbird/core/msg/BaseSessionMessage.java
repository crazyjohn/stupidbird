package com.stupidbird.core.msg;

import com.stupidbird.core.session.BaseSession;

public abstract class BaseSessionMessage<S extends BaseSession> implements SessionMessage<S> {
	protected S session;
	
	
	@Override
	public S getSession() {
		return session;
	}

	@Override
	public void setSession(S session) {
		this.session = session;
	}

}
