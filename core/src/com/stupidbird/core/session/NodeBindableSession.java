package com.stupidbird.core.session;

import org.apache.mina.core.session.IoSession;

import com.stupidbird.core.obj.SceneActiveObject;

/**
 * 绑定业务对象的会话;
 * 
 * @author crazyjohn
 *
 * @param <T>
 */
public abstract class NodeBindableSession<T extends SceneActiveObject> extends BaseSession {

	/**
	 * 获取会话的绑定对象;
	 * 
	 * @return
	 */
	public abstract T object();

	public NodeBindableSession(IoSession session) {
		super(session);
	}

}
