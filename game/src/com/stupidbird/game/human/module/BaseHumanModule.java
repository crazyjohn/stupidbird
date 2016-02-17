package com.stupidbird.game.human.module;

import com.stupidbird.core.actor.event.ActorEvent;
import com.stupidbird.core.msg.Message;
import com.stupidbird.db.entity.EntityDBService;
import com.stupidbird.game.human.Human;

public abstract class BaseHumanModule implements HumanModule {
	protected Human human;
	protected boolean isModified;

	public BaseHumanModule(Human human) {
		this.human = human;
		this.human.registerModule(this);
	}

	protected EntityDBService dbService() {
		return human.getDbService();
	}

	@Override
	public void onMessage(Message msg) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onEvent(ActorEvent event) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isModified() {
		return isModified;
	}

	@Override
	public void resetModified() {
		isModified = false;
	}

}
