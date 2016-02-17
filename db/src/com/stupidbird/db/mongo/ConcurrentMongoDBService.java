package com.stupidbird.db.mongo;

import java.net.UnknownHostException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicLong;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.stupidbird.core.concurrent.BindableExecutor;
import com.stupidbird.core.concurrent.GameExecutors;
import com.stupidbird.core.os.GameMonitor;
import com.stupidbird.db.entity.Entity;

/**
 * 并发的mongo服务;
 * 
 * @author crazyjohn
 *
 */
public class ConcurrentMongoDBService extends MongoEntityService implements Runnable {
	private static final long TYPE_BASE = 1024L;
	private MongoEntityService dbService;
	private BindableExecutor executorGroup;
	private BlockingQueue<Entity> entities = new LinkedBlockingQueue<Entity>();
	private static AtomicLong entityCounter = new AtomicLong(0);
	private ConcurrentHashMap<Class<?>, Long> types = new ConcurrentHashMap<Class<?>, Long>();
	private ExecutorService mainExecutor = Executors.newSingleThreadExecutor();
	private volatile boolean running = false;
	private Class<?>[] bindClasses;
	private Logger logger = LoggerFactory.getLogger("Data");

	public ConcurrentMongoDBService(String host, int port, String dbName, int nThreads, Class<?>[] bindClasses)
			throws UnknownHostException {
		super(host, port, dbName);
		dbService = new MongoEntityService(host, port, dbName);
		this.executorGroup = GameExecutors.newBoundableExecutor("DBExecutor", nThreads);
		this.bindClasses = bindClasses;
		// 设置key生成器
		executorGroup.setKeyGenerator((Object object) -> {
			if (!(object instanceof Entity)) {
				throw new IllegalArgumentException("R u kidding? this type: %s" + object.getClass().getSimpleName());
			}
			Entity entity = (Entity) object;
			return getHashCode(entity);
		});
	}

	@Override
	public void startup() {
		running = true;
		this.mainExecutor.execute(this);
		this.dbService.startup();
	}

	@Override
	public void shutdown() throws InterruptedException {
		running = false;
		// 先停掉主线程
		this.mainExecutor.shutdown();
		// 停掉线程组
		this.executorGroup.shutdown();
		// 停掉数据服务
		this.dbService.shutdown();
	}

	private int getHashCode(Entity entity) {
		Class<?> entityClass = entity.getClass();
		this.types.putIfAbsent(entityClass, entityCounter.incrementAndGet());
		// 计算hash
		return (int) (types.get(entityClass) * TYPE_BASE + entity.getId());
	}

	@Override
	public void update(Entity entity) {
		if (!myBusiness(entity)) {
			logger.warn(String.format("This entity is not my business: %d", entity.getClass().getSimpleName()));
			return;
		}
		entities.offer(entity);
	}

	/**
	 * is it my business?<br>
	 * FIXME: crazyjohn 目前的算法效率不优
	 * 
	 * @param entity
	 * @return
	 */
	private boolean myBusiness(Entity entity) {
		for (Class<?> eachClass : bindClasses) {
			if (eachClass == entity.getClass()) {
				return true;
			}
		}
		return false;
	}

	@Override
	public void run() {
		while (running) {
			Entity beUpdate;
			try {
				beUpdate = entities.take();
				executorGroup.execute(beUpdate, () -> {
					this.dbService.update(beUpdate);
				});
			} catch (InterruptedException e) {
				GameMonitor.catchException(e);
				break;
			} catch (Exception e) {
				GameMonitor.catchException(e);
			}

		}
		// 剩余处理
		for (Entity eachEntity : entities) {
			this.dbService.update(eachEntity);
		}
	}

}
