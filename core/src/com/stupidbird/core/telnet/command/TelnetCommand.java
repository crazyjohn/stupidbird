package com.stupidbird.core.telnet.command;

import java.util.Map;

import org.apache.mina.core.session.IoSession;

/**
 * Telnet命令;
 * 
 * @author crazyjohn
 *
 */
public abstract class TelnetCommand {
	/** Telnet命令名称 */
	private final String commandName;

	/**
	 * @param commandName
	 *            Telnet命令名称,非空
	 */
	public TelnetCommand(String commandName) {
		if (commandName == null || (commandName = commandName.trim()).length() == 0) {
			throw new IllegalArgumentException("The command name must not be null");
		}
		this.commandName = commandName;
	}

	/**
	 * 取得命令的名称
	 * 
	 * @return
	 */
	public String getCommandName() {
		return commandName;
	}

	/**
	 * 执行命令的动作
	 * 
	 * @param command
	 *            原命令
	 * @param params
	 *            从command解析得到的参数
	 * @param session
	 *            Mina IoSession
	 */
	public void exec(String command, Map<String, String> params, IoSession session) {
		sendMessage(session, this.getCommandName() + " begin");
		try {
			this.doExec(command, params, session);
		} finally {
			sendMessage(session, this.getCommandName() + " end");
		}
	}

	/**
	 * 发送Telnet行消息
	 * 
	 * @param session
	 * @param msg
	 */
	public final void sendMessage(IoSession session, String msg) {
		session.write(msg + "\r");
	}

	/**
	 * 发送错误消息
	 * 
	 * @param session
	 * @param msg
	 */
	public final void sendError(IoSession session, String msg) {
		session.write(this.commandName + " result=error,[" + msg + "]\r");
	}

	/**
	 * 执行命令逻辑
	 * 
	 * @param command
	 * @param params
	 * @param session
	 */
	protected abstract void doExec(String command, Map<String, String> params, IoSession session);

	/**
	 * 取得命令的参数,将命令后面的所有内容视为参数
	 * 
	 * @param command
	 * @return
	 */
	protected String getCommandParam(String command) {
		String jsonParam = command.substring(this.getCommandName().length()).trim();
		return jsonParam;
	}
}
