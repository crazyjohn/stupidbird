package com.stupidbird.core.actor;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public interface ActorFuture<T> {

	public T await() throws InterruptedException, ExecutionException;

	public void onSucceed(ActorCallback callback);

	public void onFailed(ActorCallback callback);

	public Object await(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException;
}
