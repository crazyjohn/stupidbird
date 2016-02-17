package com.stupidbird.core.router;

/**
 * 路由器接口
 * 
 * @author crazyjohn
 *
 */
public interface Router {
	public <R extends Routee> R select();

	public void add(Routee routee);
}
