package com.stupidbird.actor.example.cluster.transform;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

import akka.actor.ActorSystem;
import akka.actor.Props;

public class BackendMain {

	public static void main(String[] args) {
		final String port = args.length > 0 ? args[0] : "0";
		final Config config = ConfigFactory.parseString("akka.remote.netty.tcp.port=" + port)
				.withFallback(ConfigFactory.parseString("akka.cluster.roles=[backend]"))
				.withFallback(ConfigFactory.load().getConfig("Cluster"));
		ActorSystem system = ActorSystem.create("ClusterSystem", config);

		system.actorOf(Props.create(TransformationBackend.class), "backend");
	}

}
