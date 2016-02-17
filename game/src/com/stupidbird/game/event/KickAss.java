package com.stupidbird.game.event;

import com.stupidbird.core.actor.event.ActorEvent;
import com.stupidbird.core.annotation.concurrency.ImmutableUnit;

@ImmutableUnit
public class KickAss implements ActorEvent {
	private final long from;

	public KickAss(long from) {
		this.from = from;
	}

	public long from() {
		return from;
	}

}
