package com.stupidbird.core.telnet;

import java.nio.charset.Charset;

import org.apache.mina.filter.codec.textline.TextLineCodecFactory;

import com.stupidbird.core.net.IoProcessor;

/**
 * 使用Telnet协议进行交互的服务进程
 * 
 * @author crazyjohn
 * 
 */
public class TelnetIoProcessor extends IoProcessor {

	public TelnetIoProcessor(String ip, int port) {
		super(ip, port, new TextLineCodecFactory(Charset.forName("UTF-8")));
	}

}
