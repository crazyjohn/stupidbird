package com.stupidbird.core.actor;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class DefaultFuture<T> implements ActorFuture<T> {
	private Future<?> future;
	Object result;

	public DefaultFuture(Future<?> future) {
		this.future = future;
	}

	@SuppressWarnings("unchecked")
	@Override
	public T await() throws InterruptedException, ExecutionException {
		return (T) future.get();
	}

	@Override
	public void onSucceed(ActorCallback callback) {
		callback.call(result);
	}

	@Override
	public void onFailed(ActorCallback callback) {

	}

	@Override
	public Object await(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
		// TODO Auto-generated method stub
		return future.get(timeout, unit);
	}

}
