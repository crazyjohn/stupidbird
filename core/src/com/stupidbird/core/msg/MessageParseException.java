package com.stupidbird.core.msg;

/**
 * 消息解析过程发生的异常
 * 
 * 
 */
public class MessageParseException extends Exception {
	private static final long serialVersionUID = 1L;
	/** 用于指示消息来源的链路是否已经被破坏,如果已经被破坏,需要强制关闭对应的链路;否则可以不关闭 */
	private volatile boolean broken = true;

	public MessageParseException() {
		super();
	}

	public MessageParseException(String message, Throwable cause) {
		super(message, cause);
	}

	public MessageParseException(String message) {
		super(message);
	}

	public MessageParseException(Throwable cause) {
		super(cause);
	}

	/**
	 * 用于指示消息来源的链路是否已经被破坏,如果已经被破坏,需要强制关闭对应的链路不
	 * 
	 * @return the broken true,链路已经被破坏,必须关闭链路;false,链路还可以继续使用,可以不关闭链路
	 */
	public final boolean isBroken() {
		return broken;
	}

	/**
	 * @param broken
	 *            the broken to set
	 */
	public final void setBroken(boolean broken) {
		this.broken = broken;
	}
}
