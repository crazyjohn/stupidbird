package com.stupidbird.game.world;

import com.stupidbird.game.player.Player;
import com.stupidbird.game.player.PlayerActor;

/**
 * actor游戏世界, 用来管理游戏世界中的actor;
 * 
 * @author crazyjohn
 *
 */
public interface ActorWorld {

	public PlayerActor query(long playerId);

	public void enter(Player player);

	public void exit(Player player);

}
