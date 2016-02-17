package com.stupidbird.core;

/**
 * 生命周期接口;
 * 
 * @author crazyjohn
 *
 */
public interface Lifecycle {

	/**
	 * 开始生命周期;
	 * 
	 * @throws Exception
	 */
	public void startup() throws Exception;

	/**
	 * 结束生命周期;
	 * 
	 * @throws InterruptedException
	 */
	public void shutdown() throws InterruptedException;
}
