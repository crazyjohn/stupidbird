package com.stupidbird.core.concurrent;

import com.stupidbird.core.Lifecycle;

/**
 * 节点执行器;
 * 
 * @author crazyjohn
 *
 */
public interface NodeExecutor extends Lifecycle {

	/**
	 * 执行任务;
	 * 
	 * @param object
	 *            执行对象, 用来生成key;
	 * @param task
	 */
	public void execute(Object object, GameTask task);

	/**
	 * 线程信息
	 * 
	 * @return
	 */
	public String dump();

}
