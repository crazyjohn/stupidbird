package com.stupidbird.core.net.codec;

import com.stupidbird.core.msg.Message;


/**
 * The message factory;
 * 
 * @author crazyjohn
 *
 */
public interface MessageFactory {

	/**
	 * Create the message by type;
	 * 
	 * @param type
	 * @return
	 */
	public Message createMessage(int type);
}
