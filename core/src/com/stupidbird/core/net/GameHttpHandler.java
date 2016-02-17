package com.stupidbird.core.net;

import java.io.IOException;
import java.util.Map;

import com.stupidbird.core.os.GameMonitor;
import com.stupidbird.core.os.GameOSOperator;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

public class GameHttpHandler implements HttpHandler {
	@Override
	public void handle(HttpExchange httpExchange) throws IOException {
		try {
			Map<String, String> httpParams = GameOSOperator.parseHttpParam(httpExchange);
			if (httpParams.containsKey("token")) {
				
			}
		} catch (Exception e) {
			GameMonitor.catchException(e);
		}
	}
}
