package com.stupidbird.actor.example.remote;

import com.stupidbird.actor.example.local.ActorMessage;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

import akka.actor.ActorRef;
import akka.actor.ActorSelection;
import akka.actor.ActorSystem;

public class RemoteClientApp {

	public static void main(String[] args) {
		Config config = ConfigFactory.load();
		ActorSystem system = ActorSystem.create("Common", config.getConfig("Common"));
		ActorSelection selection = system.actorSelection("akka.tcp://Remote@127.0.0.1:2552/user/worker");
		// send string
		selection.tell("Hello, remote actor!", ActorRef.noSender());
		// send object
		selection.tell(ActorMessage.startup(), ActorRef.noSender());

	}
}
