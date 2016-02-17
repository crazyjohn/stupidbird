package com.stupidbird.core.concurrent;

import com.stupidbird.core.key.ObjectKeyGenerator;

/**
 * 绑定key的执行器;
 * 
 * @author crazyjohn
 *
 */
public interface BindableExecutor extends NodeExecutor {

	/**
	 * 设置Key生成器;
	 * 
	 * @param generator
	 */
	public void setKeyGenerator(ObjectKeyGenerator<?> generator);
}
