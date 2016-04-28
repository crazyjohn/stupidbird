package com.stupidbird.actor.remote;

import com.typesafe.config.ConfigFactory;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;

public class RemoteActorApp {

	public static void main(String[] args) {
		ActorSystem system = ActorSystem.create("Remote", ConfigFactory.load().getConfig("Remote"));
		// create worker
		ActorRef worker = system.actorOf(Props.create(RemoteActor.class), "worker");
		worker.tell("I am born!", ActorRef.noSender());
	}

}
