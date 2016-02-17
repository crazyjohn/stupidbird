package com.stupidbird.game.player.module;

import com.stupidbird.core.msg.Message;

/**
 * 玩家模块接口;
 * 
 * @author crazyjohn
 *
 */
public interface PlayerModule {

	public void onMessage(Message msg) throws Exception;

	public void enabled(boolean flag);

}
