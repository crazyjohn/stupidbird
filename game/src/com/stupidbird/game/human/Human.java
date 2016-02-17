package com.stupidbird.game.human;

import java.util.ArrayList;
import java.util.List;

import com.google.inject.Inject;
import com.stupidbird.core.actor.event.ActorEvent;
import com.stupidbird.core.msg.Message;
import com.stupidbird.db.entity.EntityDBService;
import com.stupidbird.game.entity.HumanEntity;
import com.stupidbird.game.event.Save;
import com.stupidbird.game.human.module.HumanModule;
import com.stupidbird.game.player.Player;

/**
 * 玩家角色的抽象, 一个玩家根据游戏类型可能会有多个角色;
 * 
 * @author crazyjohn
 *
 */
public class Human {
	/** 关联的玩家对象 */
	private Player player;
	/** 玩家相关的所有业务模块 */
	private List<HumanModule> modules = new ArrayList<HumanModule>();

	@Inject
	public Human(Player player) {
		this.player = player;
	}

	/**
	 * 注册模块;
	 * 
	 * @param module
	 */
	public void registerModule(HumanModule module) {
		modules.add(module);
	}

	/**
	 * 登陆回调;
	 * 
	 * @param humanEntity
	 */
	public void onLogin(HumanEntity humanEntity) {
		for (HumanModule eachModule : modules) {
			eachModule.onLogin(humanEntity);
		}
	}

	/**
	 * 登出回调;
	 */
	public void onLogout() {
		for (HumanModule eachModule : modules) {
			eachModule.onLogout();
		}
	}

	public Player getPlayer() {
		return player;
	}

	/**
	 * 收到外部消息回调;
	 * 
	 * @param msg
	 */
	public void onMessage(Message msg) {
		for (HumanModule eachModule : modules) {
			eachModule.onMessage(msg);
		}
	}

	/**
	 * 收到内部事件回调;
	 * 
	 * @param event
	 */
	public void onEvent(ActorEvent event) {
		// save
		if (event instanceof Save) {
			if (!isModified()) {
				return;
			}
			doSave(assembleHumanEntity());
			return;
		}
		for (HumanModule eachModule : modules) {
			eachModule.onEvent(event);
		}
	}

	/**
	 * 是否脏数据
	 * 
	 * @return
	 */
	private boolean isModified() {
		for (HumanModule eachModule : modules) {
			if (eachModule.isModified()) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 保存数据
	 * 
	 * @param humanEntity
	 */
	private void doSave(HumanEntity humanEntity) {
		getDbService().update(humanEntity);
		resetModified();
	}

	/**
	 * 重置修改位
	 */
	private void resetModified() {
		for (HumanModule eachModule : modules) {
			eachModule.resetModified();
		}
	}

	/**
	 * 装配玩家数据
	 * 
	 * @return
	 */
	private HumanEntity assembleHumanEntity() {
		HumanEntity entity = new HumanEntity();
		for (HumanModule module : this.modules) {
			module.assemble(entity);
		}
		return entity;
	}

	public EntityDBService getDbService() {
		return this.player.getDBService();
	}

}
