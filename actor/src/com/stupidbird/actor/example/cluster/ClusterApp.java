package com.stupidbird.actor.example.cluster;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

import akka.actor.ActorSystem;
import akka.actor.Props;

public class ClusterApp {

	public static void main(String[] args) {
		List<String> ports = new ArrayList<String>(Arrays.asList(new String[] { "2555", "2556" }));
		ports.stream().forEach(port -> {
			Config config = ConfigFactory.parseString("akka.remote.netty.tcp.port=" + port)
					.withFallback(ConfigFactory.load().getConfig("Cluster"));
			ActorSystem system = ActorSystem.create("ClusterSystem", config);
			system.actorOf(Props.create(SimpleClusterListener.class), "clusterListener");
		});

	}

}
