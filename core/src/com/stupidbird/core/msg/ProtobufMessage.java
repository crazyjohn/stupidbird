package com.stupidbird.core.msg;

import org.apache.mina.core.buffer.IoBuffer;

import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.Message.Builder;
import com.stupidbird.core.constants.SharedConstants;
import com.stupidbird.core.session.NodeBindableSession;

/**
 * Protobuf message;
 * 
 * @author crazyjohn
 *
 */
public class ProtobufMessage extends BaseSessionMessage<NodeBindableSession<?>> implements Message {
	protected short type;
	protected Builder builder;
	private volatile boolean alreadyParsed = false;
	private byte[] builderDatas;
	private IoBuffer buffer;
	private int messageLength;

	public ProtobufMessage(int messageType) {
		this.type = (short) messageType;
	}

	public byte[] getBuilderDatas() {
		return builderDatas;
	}

	public void setBuilderDatas(byte[] builderDatas) {
		this.builderDatas = builderDatas;
	}

	@Override
	public void setBuilder(Builder builder) {
		this.builder = builder;
	}

	@SuppressWarnings("unchecked")
	protected <B extends Builder> B parseBuilder(Builder builder) throws InvalidProtocolBufferException {
		if (this.alreadyParsed) {
			throw new IllegalStateException(String.format("This %s builder already parsed.", builder.getClass()
					.getSimpleName()));
		}
		this.builder = builder;
		this.builder = builder.mergeFrom(this.builderDatas);
		this.alreadyParsed = true;
		return (B) this.builder;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <B extends Builder> B getBuilder(Builder builder) throws InvalidProtocolBufferException {
		if (this.builder == null) {
			this.parseBuilder(builder);
		}
		return (B) this.builder;
	}

	protected boolean readBody() {
		int bodyLength = this.messageLength - SharedConstants.HEADER_SIZE;
		byte[] bodys = new byte[bodyLength];
		this.buffer.get(bodys);
		this.builderDatas = bodys;
		if (builder == null) {
			return true;
		}

		return true;
	}

	protected boolean writeBody() {
		if (builder == null) {
			if (this.builderDatas != null) {
				this.buffer.put(this.builderDatas);
			}
			return true;
		}
		this.buffer.put(builder.build().toByteArray());
		return true;
	}

	@Override
	public void read() throws MessageParseException {
		// head
		this.messageLength = buffer.getShort();
		this.type = buffer.getShort();
		// body
		this.readBody();
	}

	@Override
	public void write() {
		// head
		int pos = buffer.position();
		this.buffer.putShort((short) 0);
		this.buffer.putShort(this.type);
		this.writeBody();
		messageLength = (short) (this.buffer.position() - pos);
		this.buffer.putShort(pos, (short) messageLength);

	}

	@Override
	public short getType() {
		return type;
	}

	@Override
	public void setBuffer(IoBuffer buffer) {
		this.buffer = buffer;
	}

}
