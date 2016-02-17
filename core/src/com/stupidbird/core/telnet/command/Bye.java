package com.stupidbird.core.telnet.command;

import java.util.Map;

import org.apache.mina.core.session.IoSession;

/**
 * bye
 * 
 * @author crazyjohn
 *
 */
public class Bye extends TelnetCommand {

	public Bye(String commandName) {
		super(commandName);
	}

	@Override
	protected void doExec(String command, Map<String, String> params, IoSession session) {
		this.sendMessage(session, "bye");
		session.close(false);
	}

}
