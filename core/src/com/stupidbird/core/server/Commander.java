package com.stupidbird.core.server;

import com.stupidbird.core.config.NodeConfig;
import com.stupidbird.core.net.IoProcessor;
import com.stupidbird.core.server.telnet.TelnetNode;
import com.stupidbird.core.telnet.TelnetIoHandler;
import com.stupidbird.core.telnet.TelnetIoProcessor;
import com.stupidbird.core.telnet.command.TelnetCommand;

/**
 * 指挥官;<br>
 * private TelnetIoHandler telnetIoHandler = new TelnetIoHandler();使用字段初始化的时候<br>
 * 
 * <pre>
 * protected void assemble(ServerConfig config) {
 * 	telnetProcessor = new TelnetIoProcessor(config.getTelnetIp(), config.getTelnetPort());
 * 	// 这里回报空指针，找时间查一下fuckfuckfuck
 * 	telnetProcessor.setIoHandler(telnetIoHandler);
 * 	super.assemble(config);
 * }
 * 
 * @author crazyjohn
 *
 */
public class Commander extends ServerNode implements TelnetNode {
	/** telnet处理器 */
	protected IoProcessor telnetProcessor;
	private TelnetIoHandler telnetIoHandler;

	@Override
	protected void assemble(NodeConfig config) {
		// telnet
		telnetProcessor = new TelnetIoProcessor(config.getTelnetIp(), config.getTelnetPort());
		telnetIoHandler = new TelnetIoHandler();
		telnetProcessor.setIoHandler(telnetIoHandler);
		super.assemble(config);
	}

	@Override
	public void register(TelnetCommand command) {
		telnetIoHandler.register(command);
	}

	@Override
	public void startup() throws Exception {
		this.telnetProcessor.startup();
		super.startup();
	}

	@Override
	public void shutdown() throws InterruptedException {
		this.telnetProcessor.shutdown();
		super.shutdown();
	}

}
