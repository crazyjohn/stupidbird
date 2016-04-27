package com.stupidbird.actor;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;

public class ActorApp {

	public static void main(String[] args) {
		Config config = ConfigFactory.load();
		ActorSystem system = ActorSystem.create("CommonActor", config.getConfig("Common"));
		// create actor
		ActorRef master = system.actorOf(Props.create(MasterActor.class));
		// schedule
		Executors.newScheduledThreadPool(1).scheduleAtFixedRate(() -> {
			master.tell(ActorMessage.startup(), ActorRef.noSender());
		}, 100, 1000 * 60, TimeUnit.MILLISECONDS);

	}
}
