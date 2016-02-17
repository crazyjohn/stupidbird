package com.stupidbird.core.concurrent;

import java.util.concurrent.ExecutorService;

public interface DumpableExecutorService extends ExecutorService {
	public String dump();
}
