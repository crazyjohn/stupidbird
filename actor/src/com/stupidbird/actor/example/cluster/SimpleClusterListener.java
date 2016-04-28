package com.stupidbird.actor.example.cluster;

import akka.actor.UntypedActor;
import akka.cluster.Cluster;
import akka.cluster.ClusterEvent;
import akka.cluster.ClusterEvent.MemberEvent;
import akka.cluster.ClusterEvent.MemberRemoved;
import akka.cluster.ClusterEvent.MemberUp;
import akka.cluster.ClusterEvent.UnreachableMember;
import akka.event.Logging;
import akka.event.LoggingAdapter;

public class SimpleClusterListener extends UntypedActor {
	LoggingAdapter log = Logging.getLogger(getContext().system(), this);
	Cluster cluster = Cluster.get(getContext().system());

	@Override
	public void preStart() throws Exception {
		// subscribe
		cluster.subscribe(getSelf(), ClusterEvent.initialStateAsEvents(), MemberEvent.class, UnreachableMember.class);
	}

	@Override
	public void postStop() throws Exception {
		cluster.unsubscribe(getSelf());
	}

	@Override
	public void onReceive(Object msg) throws Exception {
		if (msg instanceof MemberUp) {
			MemberUp up = (MemberUp) msg;
			log.info("Member is up: {}", up.member());
		} else if (msg instanceof UnreachableMember) {
			UnreachableMember unReach = (UnreachableMember) msg;
			log.info("Member detected as unreadchable: {}", unReach.member());
		} else if (msg instanceof MemberRemoved) {
			MemberRemoved remove = (MemberRemoved) msg;
			log.info("Member is removed: {}", remove.member());
		} else if (msg instanceof MemberEvent) {

		} else {
			unhandled(msg);
		}
	}

}
