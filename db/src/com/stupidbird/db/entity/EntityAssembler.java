package com.stupidbird.db.entity;

import com.datastax.driver.core.Row;

/**
 * 实体装配器接口;
 * 
 * @author crazyjohn
 *
 * @param <T>
 */
public interface EntityAssembler<T> {

	/**
	 * 装配;
	 * 
	 * @param row
	 * @return
	 */
	public T assemble(Row row);

}
