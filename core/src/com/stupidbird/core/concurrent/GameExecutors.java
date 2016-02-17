package com.stupidbird.core.concurrent;

/**
 * 游戏执行器静态工厂;
 * 
 * @author crazyjohn
 *
 */
public class GameExecutors {

	public static BindableExecutor newBoundableExecutor(String executorName, int nThreads) {
		return new MultiThreadExecutor(executorName, nThreads);
	}
}
