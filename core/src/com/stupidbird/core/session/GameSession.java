package com.stupidbird.core.session;

import com.stupidbird.core.msg.Message;

/**
 * 游戏会话抽象
 * 
 * @author crazyjohn
 *
 */
public interface GameSession {

	/**
	 * Close the session;
	 */
	public void close();

	/**
	 * Write message;
	 * 
	 * @param message
	 */
	public void writeMessage(Message message);
}
