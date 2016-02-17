package com.stupidbird.core.concurrent;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.stupidbird.core.key.HashGenerator;
import com.stupidbird.core.key.ObjectKeyGenerator;

/**
 * 多线程执行器;
 * 
 * @author crazyjohn
 *
 */
public class MultiThreadExecutor implements BindableExecutor {
	private static Logger logger = LoggerFactory.getLogger("Server");
	private List<DumpableExecutorService> executors = new ArrayList<DumpableExecutorService>();
	private String name;
	private long counter;
	private ObjectKeyGenerator<Integer> generator;

	public MultiThreadExecutor(String executorName, int nThreads, ObjectKeyGenerator<Integer> generator) {
		for (int i = 0; i < nThreads; i++) {
			executors.add(newSingleDumpThreadExecutor((Runnable runnable) -> {
				return new Thread(runnable, name + "-" + counter++);
			}));
		}
		this.name = executorName;
		this.generator = generator;
	}

	DumpableExecutorService newSingleDumpThreadExecutor(ThreadFactory threadFactory) {
		return new DumpThreadPoolExecutor(1, 1, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>(),
				threadFactory);
	}

	public MultiThreadExecutor(String executorName, int nThreads) {
		this(executorName, nThreads, new HashGenerator());
	}

	@Override
	public void execute(Object object, GameTask task) {
		ExecutorService subExecutor = executor(object);
		subExecutor.execute(task);
	}

	protected ExecutorService executor(Object object) {
		return this.executors.get(this.generator.generate(object) % this.executors.size());
	}

	@Override
	public void shutdown() throws InterruptedException {
		CountDownLatch stopLatch = new CountDownLatch(this.executors.size());
		for (ExecutorService eachService : this.executors) {
			eachService.submit(new Poision(stopLatch));
		}
		// 等待死亡
		stopLatch.await();
		// 直接停掉
		for (ExecutorService eachService : this.executors) {
			eachService.shutdownNow();
		}
	}

	/**
	 * 毒药总是最后一个任务;
	 * 
	 * @author crazyjohn;
	 *
	 */
	public static class Poision implements Runnable {
		CountDownLatch stopLatch;

		public Poision(CountDownLatch stopLatch) {
			this.stopLatch = stopLatch;
		}

		@Override
		public void run() {
			stopLatch.countDown();
			logger.info(String.format("Eat this and go to the hell! poor guy: %s", Thread.currentThread().getName()));
		}

	}

	@SuppressWarnings("unchecked")
	@Override
	public void setKeyGenerator(ObjectKeyGenerator<?> generator) {
		this.generator = (ObjectKeyGenerator<Integer>) generator;
	}

	@Override
	public void startup() throws Exception {
		// do nothing

	}

	@Override
	public String dump() {
		StringBuilder sb = new StringBuilder("\n");
		int index = 0;
		for (DumpableExecutorService executor : this.executors) {
			sb.append(name).append("-").append(index).append(" => ").append(executor.dump()).append("\n");
			index++;
		}
		return sb.toString();
	}

}
