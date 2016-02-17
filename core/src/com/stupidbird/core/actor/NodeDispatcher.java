package com.stupidbird.core.actor;

import com.stupidbird.core.msg.Message;
import com.stupidbird.core.server.Node;

/**
 * 分发器接口;
 * <p>
 * 职责是对 {@link Node} 接收到的消息进行正确的分发
 * 
 * @author crazyjohn
 *
 */
public interface NodeDispatcher {

	/**
	 * 消息分发;
	 * 
	 * @param msg
	 */
	public void dispatch(Message msg);

}
