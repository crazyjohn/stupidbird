package com.stupidbird.core.net;

import java.io.IOException;
import java.net.InetSocketAddress;

import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.core.service.IoHandler;
import org.apache.mina.filter.codec.ProtocolCodecFactory;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The net io processor;
 * 
 * @author crazyjohn
 *
 */
public class IoProcessor {
	private Logger logger = LoggerFactory.getLogger("Server");
	/** Acceptor */
	protected IoAcceptor acceptor;
	/** port */
	protected int port;
	/** bindIp */
	protected String bindIp;
	/** ioHandler */
	protected IoHandler ioHandler;
	/** codec factory */
	protected ProtocolCodecFactory codecFactory;

	public IoProcessor(String bindIp, int port, ProtocolCodecFactory codecFactory) {
		acceptor = new NioSocketAcceptor();
		this.bindIp = bindIp;
		this.port = port;
		this.codecFactory = codecFactory;
	}

	public void startup() throws IOException {
		acceptor.setHandler(ioHandler);
		acceptor.getFilterChain().addLast("codec", new ProtocolCodecFilter(codecFactory));
		acceptor.bind(new InetSocketAddress(this.bindIp, this.port));
		logger.info(String.format("IoProcessor bind info => ip: %s, port: %d", bindIp, port));
	}

	public void shutdown() {
		// unbind
		acceptor.unbind();
		// dispose
		acceptor.dispose();
	}

	public void setIoHandler(IoHandler handler) {
		this.ioHandler = handler;
	}

}
