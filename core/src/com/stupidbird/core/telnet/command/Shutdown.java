package com.stupidbird.core.telnet.command;

import java.util.Map;

import org.apache.mina.core.session.IoSession;

/**
 * 停机;
 * 
 * @author crazyjohn
 *
 */
public class Shutdown extends TelnetCommand {

	public Shutdown(String commandName) {
		super(commandName);
	}

	@Override
	protected void doExec(String command, Map<String, String> params, IoSession session) {
		// 用另外的线程去处理，否则会导致死锁。原因是此处会导致Mina的io线程阻塞去等待管理的会话全部挂掉，但是挂掉也同样需要本线程去刷新，所以死锁
		// FIXME: crazyjohn 可以使用executor去执行，尽量不对外暴露线程
		new Thread(() -> {
			sendMessage(session, "holy shit, you'll destory everything, i'll be die.");
			session.close(false);
			System.exit(0);
		}).start();
	}

}
