package com.stupidbird.core.net.codec;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoder;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;

import com.stupidbird.core.constants.SharedConstants;
import com.stupidbird.core.msg.Message;

/**
 * The game encoder;
 * 
 * @author crazyjohn
 *
 */
public class GameEncoder implements ProtocolEncoder {

	@Override
	public void encode(IoSession session, Object message, ProtocolEncoderOutput out) throws Exception {
		Message msg = (Message) message;
		IoBuffer writeBuffer = IoBuffer.allocate(SharedConstants.ENCODE_MESSAGE_LENGTH);
		msg.setBuffer(writeBuffer);
		msg.write();
		int length = writeBuffer.position();
		byte[] datas = new byte[length];
		writeBuffer.flip();
		writeBuffer.get(datas);
		IoBuffer messageBuffer = IoBuffer.wrap(datas);
		out.write(messageBuffer);
	}

	@Override
	public void dispose(IoSession session) throws Exception {
		// do nothing
	}

}
