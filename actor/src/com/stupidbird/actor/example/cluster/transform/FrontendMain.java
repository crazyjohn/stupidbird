package com.stupidbird.actor.example.cluster.transform;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import com.stupidbird.actor.example.cluster.transform.TransformationMessage.TransformationJob;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.dispatch.OnSuccess;
import akka.pattern.Patterns;
import akka.util.Timeout;
import scala.concurrent.ExecutionContext;
import scala.concurrent.duration.Duration;
import scala.concurrent.duration.FiniteDuration;

public class FrontendMain {

	public static void main(String[] args) {
		final String port = args.length > 0 ? args[0] : "0";
		final Config config = ConfigFactory.parseString("akka.remote.netty.tcp.port=" + port)
				.withFallback(ConfigFactory.parseString("akka.cluster.roles = [frontend]"))
				.withFallback(ConfigFactory.load().getConfig("Cluster"));
		// system
		ActorSystem system = ActorSystem.create("ClusterSystem", config);
		final ActorRef frontend = system.actorOf(Props.create(TransformationFrontend.class), "frontend");
		final FiniteDuration interval = Duration.create(2, TimeUnit.SECONDS);
		final Timeout timeout = new Timeout(Duration.create(5, TimeUnit.SECONDS));
		final ExecutionContext context = system.dispatcher();
		final AtomicInteger counter = new AtomicInteger();
		system.scheduler().schedule(interval, interval, () -> {
			Patterns.ask(frontend, new TransformationJob("hello-" + counter.incrementAndGet()), timeout)
					.onSuccess(new OnSuccess<Object>() {
						@Override
						public void onSuccess(Object result) throws Throwable {
							System.out.println(result);
						}

					}, context);
		}, context);
	}

}
