package com.stupidbird.actor.example.cluster.transform;

import com.stupidbird.actor.example.cluster.transform.TransformationMessage.TransformationJob;
import com.stupidbird.actor.example.cluster.transform.TransformationMessage.TransformationResult;

import akka.actor.UntypedActor;
import akka.cluster.Cluster;
import akka.cluster.ClusterEvent.CurrentClusterState;
import akka.cluster.ClusterEvent.MemberUp;
import akka.cluster.Member;
import akka.cluster.MemberStatus;

public class TransformationBackend extends UntypedActor {
	Cluster cluster = Cluster.get(getContext().system());

	@Override
	public void preStart() throws Exception {
		cluster.subscribe(getSelf(), MemberUp.class);
	}

	@Override
	public void postStop() throws Exception {
		cluster.unsubscribe(getSelf());
	}

	@Override
	public void onReceive(Object msg) throws Exception {
		if (msg instanceof MemberUp) {
			MemberUp up = (MemberUp) msg;
			register(up.member());
		} else if (msg instanceof TransformationJob) {
			TransformationJob job = (TransformationJob) msg;
			getSender().tell(new TransformationResult(job.getText().toUpperCase()), getSelf());
		} else if (msg instanceof CurrentClusterState) {
			CurrentClusterState state = (CurrentClusterState) msg;
			state.getMembers().forEach(member -> {
				if (member.status().equals(MemberStatus.up())) {
					register(member);
				}
			});
		} else {
			unhandled(msg);
		}
	}

	/**
	 * register to frontend
	 * 
	 * @param member
	 */
	private void register(Member member) {
		if (member.hasRole("frontend")) {
			getContext().actorSelection(member.address() + "/user/frontend")
					.tell(TransformationMessage.BACKEND_REGISTRATION, getSelf());
		}
	}

}
