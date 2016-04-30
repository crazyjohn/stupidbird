package com.stupidbird.actor.example.local;

import java.io.Serializable;

public class ActorMessage {

	public static Startup startup() {
		return new Startup();
	}

	static class Startup implements Serializable {
		private static final long serialVersionUID = 1L;
	}

}
