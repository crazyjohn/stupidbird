package com.stupidbird.core.router;

import java.util.ArrayList;
import java.util.List;

/**
 * 轮询路由器
 * 
 * @author crazyjohn
 *
 */
public class RoundRobinRouter implements Router {
	protected List<Routee> routees = new ArrayList<Routee>();;

	@Override
	public <R extends Routee> R select() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void add(Routee routee) {
		routees.add(routee);
	}

}
