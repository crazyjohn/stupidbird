package com.stupidbird.core.concurrent;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class DumpThreadPoolExecutor extends ThreadPoolExecutor implements DumpableExecutorService {

	public DumpThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit,
			BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory) {
		super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory);
	}

	@Override
	public String dump() {
		StringBuilder sb = new StringBuilder();
		sb.append("{taskCount: ").append(this.getTaskCount()).append(", ");
		sb.append("completedTaskCount: ").append(this.getCompletedTaskCount()).append("}");
		return sb.toString();
	}

}
