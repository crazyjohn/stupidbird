package com.stupidbird.actor.remote;

import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

public class RemoteActor extends UntypedActor {
	LoggingAdapter log = Logging.getLogger(getContext().system(), this);

	@Override
	public void onReceive(Object msg) throws Exception {
		if (msg instanceof String) {
			log.info("Received msg: {}", msg);
		} else {
			log.info("Received msg: {}", msg.getClass().getSimpleName());
		}
	}

}
