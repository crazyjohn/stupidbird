package com.stupidbird.core.actor;

import com.stupidbird.core.actor.event.ActorEvent;
import com.stupidbird.core.concurrent.NodeExecutor;

public class BaseActor implements Actor {
	protected NodeExecutor executor;

	public BaseActor(NodeExecutor executor) {
		this.executor = executor;
	}

	protected void onEvent(ActorEvent event) {
		// hook
	}

	@Override
	public void tell(ActorEvent event) {
		executor.execute(hostedObject(), () -> {
			onEvent(event);
		});
	}

	protected Object hostedObject() {
		return this;
	}

}
