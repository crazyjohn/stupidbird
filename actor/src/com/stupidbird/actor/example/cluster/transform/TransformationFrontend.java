package com.stupidbird.actor.example.cluster.transform;

import java.util.ArrayList;
import java.util.List;

import com.stupidbird.actor.example.cluster.transform.TransformationMessage.JobFailed;
import com.stupidbird.actor.example.cluster.transform.TransformationMessage.TransformationJob;

import akka.actor.ActorRef;
import akka.actor.Terminated;
import akka.actor.UntypedActor;

public class TransformationFrontend extends UntypedActor {
	List<ActorRef> backends = new ArrayList<ActorRef>();
	int jobCounter = 0;

	@Override
	public void onReceive(Object msg) throws Exception {
		if ((msg instanceof TransformationJob) && backends.isEmpty()) {
			TransformationJob job = (TransformationJob) msg;
			getSender().tell(new JobFailed("Service unavailable, try again later", job), getSender());
		} else if (msg instanceof TransformationJob) {
			TransformationJob job = (TransformationJob) msg;
			jobCounter++;
			backends.get(jobCounter % backends.size()).forward(job, getContext());
		} else if (msg.equals(TransformationMessage.BACKEND_REGISTRATION)) {
			// watch
			getContext().watch(getSender());
			backends.add(getSender());
		} else if (msg instanceof Terminated) {
			// terminated
			Terminated terminated = (Terminated) msg;
			backends.remove(terminated.getActor());
		} else if (msg instanceof JobFailed) {
			JobFailed failed = (JobFailed) msg;
			System.out.println(failed.getReason());
		} else {
			unhandled(msg);
		}
	}

}
