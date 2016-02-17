package com.stupidbird.core.net.codec;

import com.stupidbird.core.msg.Message;
import com.stupidbird.core.msg.ProtobufMessage;

/**
 * protobuf消息工厂;
 * 
 * @author crazyjohn
 *
 */
public class ProtobufMessageFactory implements MessageFactory {

	@Override
	public Message createMessage(int type) {
		ProtobufMessage message = new ProtobufMessage(type);
		return message;
	}
}
