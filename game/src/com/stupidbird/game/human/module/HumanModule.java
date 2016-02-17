package com.stupidbird.game.human.module;

import com.stupidbird.core.actor.event.ActorEvent;
import com.stupidbird.core.msg.Message;
import com.stupidbird.game.entity.HumanEntity;

/**
 * 角色模块;<br>
 * 这是基础的实现，角色的每个模块都回去管理模块相关的数据，然后对外暴露和其它模块交互的接口，接口和实现之间设想会委托guice去做依赖注入<br>
 * 
 * @author crazyjohn
 *
 */
public interface HumanModule {

	/**
	 * 处理外部消息
	 * 
	 * @param msg
	 */
	public void onMessage(Message msg);

	/**
	 * 处理内部事件
	 * 
	 * @param event
	 */
	public void onEvent(ActorEvent event);

	/**
	 * 登陆处理
	 * 
	 * @param entity
	 */
	public void onLogin(HumanEntity entity);

	/**
	 * 登出处理
	 */
	public void onLogout();

	/**
	 * 装配数据
	 * 
	 * @param entity
	 */
	public void assemble(HumanEntity entity);

	/**
	 * 是否修改
	 * 
	 * @return
	 */
	public boolean isModified();

	/**
	 * 重置修改
	 */
	public void resetModified();

}
