package com.stupidbird.cluster;

import com.stupidbird.core.server.cluster.NodeInfo;
import com.stupidbird.core.server.telnet.TelnetNode;

/**
 * 注册表
 * 
 * @author crazyjohn
 *
 */
public interface Registry extends TelnetNode {

	/**
	 * 根据id查询节点信息
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public NodeInfo query(int id) throws Exception;

	/**
	 * 注册节点信息
	 * 
	 * @param info
	 * @throws Exception
	 */
	public void register(NodeInfo info) throws Exception;

	/**
	 * 选择节点
	 * 
	 * @return
	 */
	public NodeInfo select();

}
