package com.stupidbird.core.server.telnet;

import com.stupidbird.core.telnet.command.TelnetCommand;

public interface TelnetNode {
	public void register(TelnetCommand command);
}
