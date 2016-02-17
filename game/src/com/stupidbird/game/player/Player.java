package com.stupidbird.game.player;

import com.google.inject.Injector;
import com.google.inject.Module;
import com.google.protobuf.Message.Builder;
import com.stupidbird.core.actor.event.ActorEvent;
import com.stupidbird.core.msg.Message;
import com.stupidbird.core.msg.ProtobufMessage;
import com.stupidbird.core.obj.SceneActiveObject;
import com.stupidbird.db.entity.EntityDBService;
import com.stupidbird.db.uuid.UUIDGenerator;
import com.stupidbird.game.entity.PlayerEntity;
import com.stupidbird.game.human.Human;
import com.stupidbird.game.io.PlayerSession;
import com.stupidbird.game.player.module.PlayerLoginModule;
import com.stupidbird.game.player.module.PlayerModule;

/**
 * 玩家抽象
 * 
 * @author crazyjohn
 *
 */
public class Player implements SceneActiveObject {
	private Injector globalInjector;
	private PlayerSession session;
	private PlayerEntity playerEntity;
	private PlayerModule loginModule;
	private EntityDBService dbService;
	private UUIDGenerator uuidService;
	private Human human;

	public Player(PlayerSession session, EntityDBService dbService, UUIDGenerator uuidService, Injector globalInjector) {
		this.session = session;
		this.dbService = dbService;
		this.uuidService = uuidService;
		this.globalInjector = globalInjector;
		loginModule = new PlayerLoginModule(this);
	}

	@Override
	public long getId() {
		return playerEntity != null ? playerEntity.getId() : 0;
	}

	public void sendMessage(int type, Builder builder) {
		ProtobufMessage msg = new ProtobufMessage(type);
		msg.setBuilder(builder);
		this.session.writeMessage(msg);
	}

	@Override
	public void onMessage(Message msg) throws Exception {
		loginModule.onMessage(msg);
		// 角色
		if (this.human != null) {
			human.onMessage(msg);
		}
	}

	@Override
	public void onEvent(ActorEvent event) {
		// 角色
		if (this.human != null) {
			human.onEvent(event);
		}
	}

	public EntityDBService getDBService() {
		return dbService;
	}

	public void setPlayerEntity(PlayerEntity entity) {
		this.playerEntity = entity;
	}

	public void setHuman(Human human) {
		this.human = human;
	}

	public Human getHuman() {
		return human;
	}

	public UUIDGenerator getUUIDService() {
		return uuidService;
	}

	public Injector createChildInjector(Module... modules) {
		return globalInjector.createChildInjector(modules);
	}

	public <T> T globalInjector(Class<T> instanceClass) {
		return this.globalInjector.getInstance(instanceClass);
	}

}
