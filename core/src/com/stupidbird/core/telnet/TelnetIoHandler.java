package com.stupidbird.core.telnet;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.transport.socket.SocketSessionConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.stupidbird.core.constants.SharedConstants;
import com.stupidbird.core.telnet.command.Auth;
import com.stupidbird.core.telnet.command.Bye;
import com.stupidbird.core.telnet.command.Shutdown;
import com.stupidbird.core.telnet.command.TelnetCommand;

/**
 * Telnet协议处理
 * 
 * @author crazyjohn
 * 
 */
public class TelnetIoHandler extends IoHandlerAdapter {
	private static final Logger logger = LoggerFactory.getLogger("Telnet");
	private static final long MAX_NO_AUTHED_SPEAK_TIMES = 5;
	private static final String AUTH_COMMAND = "AUTH";
	private static final String COUNTER = "COUNTER";
	/** 注册Telnet命令 */
	private final Map<String, TelnetCommand> commands = new HashMap<String, TelnetCommand>();

	{
		this.register(new Bye("bye"));
		this.register(new Shutdown("shutdown"));
		this.register(new Auth("auth"));
	}

	/**
	 * 注册命令
	 * 
	 * @param command
	 */
	public void register(TelnetCommand command) {
		if (this.commands.containsKey(command.getCommandName())) {
			if (logger.isWarnEnabled()) {
				logger.warn("The command [" + command.getCommandName()
						+ "] has been already registed,which will be replaced.");
			}
		}
		this.commands.put(command.getCommandName().toUpperCase(), command);
	}

	@Override
	public void messageReceived(IoSession session, Object msg) throws Exception {
		final String msgInfo = msg.toString().trim();
		if (msgInfo.length() == 0) {
			return;
		}
		// 解析命令
		final String[] msgArray = msgInfo.split(" ");
		final String cmd = msgArray[0].toUpperCase();
		// 是否认证
		increaseCounter(session);
		// 违规踢出
		kickFucker(session);
		if (!authed(session) && !isAuthCommand(cmd)) {
			who(session);
			return;
		}
		// 找到命令
		final TelnetCommand command = this.commands.get(cmd);
		if (command == null) {
			if (logger.isWarnEnabled()) {
				logger.warn("No registed command [" + cmd + "]");
			}
			session.write("Unknown command:" + cmd + "\r");
			return;
		}

		Map<String, String> _cmdParamMap = parseParamMap(msgArray);
		command.exec(msgInfo, _cmdParamMap, session);
		doLog(session, cmd);
	}

	private void doLog(IoSession session, String cmd) {
		logger.info(String.format("User: %s execute this Command: %s", getName(session), cmd));
	}

	private String getName(IoSession session) {
		String name = (String) session.getAttribute(SharedConstants.TELNET_USER);
		return name == null ? "Unknow" : name;
	}

	/**
	 * 是否认证命令
	 * 
	 * @param cmd
	 * @return
	 */
	private boolean isAuthCommand(String cmd) {
		return AUTH_COMMAND.equals(cmd);
	}

	/**
	 * 是否已经认证过了
	 * 
	 * @param session
	 * @return
	 */
	private boolean authed(IoSession session) {
		return session.getAttribute(SharedConstants.TELNET_USER) != null;
	}

	@Override
	public void sessionClosed(IoSession session) throws Exception {
		logger.info(String.format("Telnet session closed: %s", session));
	}

	@Override
	public void sessionCreated(IoSession session) throws Exception {
		// if (session.getTransportType() == TransportType.SOCKET) {
		((SocketSessionConfig) session.getConfig()).setReceiveBufferSize(2048);
		// }
	}

	@Override
	public void sessionOpened(IoSession session) throws Exception {
		logger.info(String.format("Telnet session opened: %s", session));
		// say hi
		who(session);
		// initCounter
		initCounter(session);
	}

	private void initCounter(IoSession session) {
		AtomicLong speakCounter = new AtomicLong(0);
		session.setAttribute(COUNTER, speakCounter);
	}

	private long getCounter(IoSession session) {
		AtomicLong speakCounter = (AtomicLong) session.getAttribute(COUNTER);
		return speakCounter.get();
	}

	private void who(IoSession session) {
		session.write("who r u?\r");
	}

	private void increaseCounter(IoSession session) {
		AtomicLong speakCounter = (AtomicLong) session.getAttribute(COUNTER);
		speakCounter.incrementAndGet();
	}

	/**
	 * 是否需要踢掉
	 * 
	 * @param session
	 */
	private void kickFucker(IoSession session) {
		if (!authed(session) && getCounter(session) >= MAX_NO_AUTHED_SPEAK_TIMES) {
			session.write("son of bitch, fuck off!!!\r");
			session.close(false);
		}
	}

	@Override
	public void exceptionCaught(IoSession session, Throwable cause) throws Exception {
		if (logger.isErrorEnabled()) {
			logger.error("Exception", cause);
		}
		// session.close();
	}

	/**
	 * 解析命令中的参数,将所有格式为[param]=[value]格式的内容,以param为key,以value为值放到到map中
	 * 
	 * @param commandParams
	 * @return
	 */
	private Map<String, String> parseParamMap(String[] commandParams) {
		Map<String, String> map = new HashMap<String, String>();
		for (int i = 1; i < commandParams.length; i++) {
			String param = commandParams[i];
			String[] kv = param.split("=");
			if (kv != null) {
				if (kv.length == 2) {
					map.put(kv[0], kv[1]);
				} else {
					if (logger.isDebugEnabled()) {
						logger.debug("Skip param [" + param + "]");
					}
				}
			}
		}
		return map;
	}
}
