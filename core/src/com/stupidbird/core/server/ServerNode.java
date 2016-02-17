package com.stupidbird.core.server;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.stupidbird.core.Lifecycle;
import com.stupidbird.core.actor.NodeDispatcher;
import com.stupidbird.core.concurrent.NodeExecutor;
import com.stupidbird.core.concurrent.GameExecutors;
import com.stupidbird.core.config.NodeConfig;
import com.stupidbird.core.constants.SharedConstants;
import com.stupidbird.core.msg.Message;
import com.stupidbird.core.net.NodeIoHandler;
import com.stupidbird.core.net.IoProcessor;
import com.stupidbird.core.net.codec.GameCodecFactory;
import com.stupidbird.core.net.codec.ProtobufMessageFactory;
import com.stupidbird.core.os.GameMonitor;

/**
 * 服务器节点;
 * 
 * @author crazyjohn
 *
 */
public class ServerNode implements Node, NodeDispatcher {
	private Logger logger = LoggerFactory.getLogger("Server");
	/** 消息处理线程池 */
	protected NodeExecutor msgExecutor;
	/** 网络Io处理器 */
	protected IoProcessor ioProcessor;
	/** 分发器 */
	protected NodeDispatcher dispatcher;
	/** 心跳计数器 */
	protected AtomicLong tickCounter = new AtomicLong(0);
	/** 心跳部件 */
	protected Map<Long, Tickable> ticks = new ConcurrentHashMap<Long, Tickable>();
	private ExecutorService tickExecutor = Executors.newSingleThreadExecutor();
	/** 停机处理钩子 */
	protected List<Runnable> hooks = new CopyOnWriteArrayList<Runnable>();
	private volatile boolean running = false;
	protected NodeConfig defaultConfig = new NodeConfig();
	protected List<Lifecycle> lifecycles = new CopyOnWriteArrayList<Lifecycle>();

	public ServerNode() {
		assemble(this.defaultConfig);
	}

	/**
	 * 装配;
	 * 
	 * @param config
	 * @param ioHandler
	 */
	protected void assemble(NodeConfig config) {
		// 网络
		ioProcessor = new IoProcessor(config.getBindIp(), config.getPort(), new GameCodecFactory(
				new ProtobufMessageFactory()));
		// 消息处理
		msgExecutor = GameExecutors.newBoundableExecutor("GameMessageExecutor", config.getMsgThreadCount());
		logger.info(String.format("Game thread count: %d", config.getMsgThreadCount()));
		// 分发器
		dispatcher = new DefaultDispatcher(msgExecutor);
		this.defaultConfig = config;
	}

	@Override
	public void configure(NodeConfig config) throws Exception {
		assemble(config);
	}

	@Override
	public void startup() throws Exception {
		running = true;
		// 启动网络Io处理器
		logger.info("Startup io processor...");
		ioProcessor.startup();
		logger.info("IoProcessor already started.");
		logger.info("The ServerNode already started.");
		// 启动主线程心跳
		tickExecutor.execute(() -> {
			tick();
		});
		// 启动生命周期
		for (Lifecycle cycle : lifecycles) {
			cycle.startup();
		}
		// 注册钩子
		this.registerShutdownHook(() -> {
			try {
				shutdown();
			} catch (Exception e) {
				GameMonitor.catchException(e);
			}
		});
		// 停机处理
		Runtime.getRuntime().addShutdownHook(new Thread("ShutdownHooksThread") {
			@Override
			public void run() {
				hooks.forEach((Runnable hook) -> {
					hook.run();
				});
			}
		});
	}

	@Override
	public void shutdown() throws InterruptedException {
		running = false;
		// 停止网络io处理器
		logger.info("Shutdown io processor...");
		ioProcessor.shutdown();
		logger.info("IoProcessor already shutdown.");
		this.msgExecutor.shutdown();
		this.tickExecutor.shutdownNow();
		// 终止生命周期
		for (Lifecycle cycle : lifecycles) {
			cycle.shutdown();
		}
	}

	@Override
	public void dispatch(Message msg) {
		if (dispatcher == null) {
			return;
		}
		dispatcher.dispatch(msg);
	}

	@Override
	public void setDispatcher(NodeDispatcher dispatcher) {
		this.dispatcher = dispatcher;
	}

	@Override
	public void registerTick(Tickable tick) {
		ticks.put(tickCounter.incrementAndGet(), tick);
	}

	/**
	 * 执行心跳
	 */
	protected void tick() {
		Thread.currentThread().setName("GameTickExecutor");
		while (running) {
			try {
				// 主线程休眠
				TimeUnit.MILLISECONDS.sleep(SharedConstants.MAIN_THREAD_TICK_INTERVAL);
				// 派发心跳
				for (Entry<Long, Tickable> entry : this.ticks.entrySet()) {
					this.msgExecutor.execute(entry.getKey(), () -> {
						entry.getValue().tick();
					});
				}
			} catch (InterruptedException e) {
				// 处理中断，直接跳出来
				GameMonitor.catchException(e);
			} catch (Exception e) {
				// 捕获其它异常
				GameMonitor.catchException(e);
			}

		}

	}

	@Override
	public NodeExecutor executor() {
		return msgExecutor;
	}

	@Override
	public void registerShutdownHook(Runnable runnable) {
		this.hooks.add(runnable);
	}

	@Override
	public void setIoHandler(NodeIoHandler<?> handler) {
		ioProcessor.setIoHandler(handler);
	}

	@Override
	public void registerLifecycle(Lifecycle cycle) {
		lifecycles.add(cycle);
	}
}
