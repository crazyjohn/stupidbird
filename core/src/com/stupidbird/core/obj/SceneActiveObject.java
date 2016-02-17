package com.stupidbird.core.obj;

import com.stupidbird.core.actor.event.ActorEvent;
import com.stupidbird.core.msg.Message;

/**
 * 场景对象抽象
 * 
 * @author crazyjohn
 *
 */
public interface SceneActiveObject {

	public long getId();

	/**
	 * 响应消息
	 * 
	 * @param msg
	 * @throws Exception
	 */
	public void onMessage(Message msg) throws Exception;

	/**
	 * 响应事件
	 * 
	 * @param event
	 */
	public void onEvent(ActorEvent event);
}
