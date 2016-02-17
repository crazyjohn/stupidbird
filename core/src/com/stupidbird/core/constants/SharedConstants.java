package com.stupidbird.core.constants;

/**
 * 全局共享的常量
 * 
 * 
 * @author crazyjohn;
 */
public interface SharedConstants {

	/** 系统默认的编码,UTF-8 {@index} */
	public static final String DEFAULT_CHARSET = "UTF-8";
	
	
	
	public static final int HEADER_SIZE = 4;
	public static final int DECODE_MESSAGE_LENGTH = 64;
	public static final int ENCODE_MESSAGE_LENGTH = 512;



	public static final long MAIN_THREAD_TICK_INTERVAL = 100;



	public static final String TELNET_USER = "NAMED";
}
