package com.stupidbird.core.telnet.command;

import java.util.Map;

import org.apache.mina.core.session.IoSession;

import com.stupidbird.core.constants.SharedConstants;

/**
 * 认证命令
 * 
 * @author crazyjohn
 *
 */
public class Auth extends TelnetCommand {
	String name = "crazyjohn";
	String password = "crazyjohn";

	public Auth(String commandName) {
		super(commandName);
	}

	@Override
	protected void doExec(String command, Map<String, String> params, IoSession session) {
		String name = params.get("u");
		String password = params.get("p");
		if (name == null || password == null) {
			return;
		}
		if (this.name.equals(name) && this.password.equals(password)) {
			this.sendMessage(session, "nice to meet you " + name);
			authed(session);
		} else {
			this.sendError(session, "r u kidding??? fuck off!!! " + name);
		}
	}

	private void authed(IoSession session) {
		session.setAttribute(SharedConstants.TELNET_USER, name);
	}

}
