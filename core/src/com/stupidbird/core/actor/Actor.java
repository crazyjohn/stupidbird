package com.stupidbird.core.actor;

import com.stupidbird.core.actor.event.ActorEvent;

/**
 * Actor对象接口;
 * 
 * @author crazyjohn
 *
 */
public interface Actor {

	/**
	 * actor之间通信的方式;
	 * <p>
	 * tell and forget;
	 * 
	 * @param event
	 */
	public void tell(ActorEvent event);

}
