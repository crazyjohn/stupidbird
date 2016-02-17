package com.stupidbird.core.server;

import com.stupidbird.core.Lifecycle;
import com.stupidbird.core.actor.NodeDispatcher;
import com.stupidbird.core.concurrent.NodeExecutor;
import com.stupidbird.core.config.NodeConfig;
import com.stupidbird.core.net.NodeIoHandler;

/**
 * 服务器节点抽象;
 * 
 * @author crazyjohn
 *
 */
public interface Node extends NodeDispatcher, Lifecycle {

	/**
	 * 配置节点;
	 * 
	 * @param config
	 * @throws Exception
	 */
	public void configure(NodeConfig config) throws Exception;

	/**
	 * 设置分发器;
	 * 
	 * @param dispatcher
	 */
	public void setDispatcher(NodeDispatcher dispatcher);

	/**
	 * 注册可心跳部件;
	 * 
	 * @param tick
	 */
	public void registerTick(Tickable tick);

	/**
	 * 注册生命周期
	 * 
	 * @param cycle
	 */
	public void registerLifecycle(Lifecycle cycle);

	/**
	 * 注册停机钩子;
	 * 
	 * @param runnable
	 */
	public void registerShutdownHook(Runnable runnable);

	/**
	 * 设置io处理器;
	 * 
	 * @param handler
	 */
	public void setIoHandler(NodeIoHandler<?> handler);

	/**
	 * 节点执行器;
	 * 
	 * @return
	 */
	public NodeExecutor executor();

}
