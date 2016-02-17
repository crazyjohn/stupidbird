package com.stupidbird.core.msg;

import org.apache.mina.core.buffer.IoBuffer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.Message.Builder;
import com.stupidbird.core.session.BaseSession;

/**
 * 內部消息;
 * 
 * @author crazyjohn
 *
 * @param <S>
 */
public abstract class Internal<S extends BaseSession> extends BaseSessionMessage<S> {
	protected Logger logger = LoggerFactory.getLogger("Message");

	/**
	 * 执行;
	 */
	public abstract void execute();

	@Override
	public short getType() {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setBuilder(Builder builder) {
		throw new UnsupportedOperationException();
	}

	@Override
	public <B extends Builder> B getBuilder(Builder builder) throws InvalidProtocolBufferException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setBuffer(IoBuffer aMessageBuffer) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void read() throws MessageParseException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void write() {
		throw new UnsupportedOperationException();
	}
}
