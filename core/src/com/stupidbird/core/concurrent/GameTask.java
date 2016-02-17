package com.stupidbird.core.concurrent;

/**
 * 游戏任务;
 * 
 * @author crazyjohn
 *
 */
public interface GameTask extends Runnable {

	/**
	 * 运行任务;
	 */
	public void run();
}
