package com.stupidbird.core.msg;

import com.stupidbird.core.session.GameSession;

/**
 * 回话消息接口;
 * 
 * @author crazyjohn
 *
 */
public interface SessionMessage<S extends GameSession> extends Message {
	/**
	 * 获取绑定的回话;
	 * 
	 * @return
	 */
	public S getSession();

	/**
	 * 设置会话;
	 * 
	 * @param session
	 */
	public void setSession(S session);
}
