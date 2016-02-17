package com.stupidbird.core.msg;

import org.apache.mina.core.buffer.IoBuffer;

import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.Message.Builder;

/**
 * 消息抽象;
 * 
 * @author crazyjohn
 *
 */
public interface Message {

	public short getType();

	public void setBuilder(Builder builder);

	public <B extends Builder> B getBuilder(Builder builder) throws InvalidProtocolBufferException;

	public void setBuffer(IoBuffer aMessageBuffer);

	public void read() throws MessageParseException;

	public void write();
}
