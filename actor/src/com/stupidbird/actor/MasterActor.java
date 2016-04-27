package com.stupidbird.actor;

import com.stupidbird.actor.ActorMessage.Startup;

import akka.actor.UntypedActor;

public class MasterActor extends UntypedActor {

	@Override
	public void onReceive(Object msg) throws Exception {
		if (msg instanceof Startup) {
			startup();
		}
	}

	private void startup() {
		System.out.println("Startup");
	}

}
