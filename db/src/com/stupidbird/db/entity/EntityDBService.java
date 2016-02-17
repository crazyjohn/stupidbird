package com.stupidbird.db.entity;

import java.io.Serializable;
import java.util.List;

import com.stupidbird.db.DBService;

/**
 * 实体数据服务接口;
 * 
 * @author crazyjohn
 *
 */
public interface EntityDBService extends DBService {

	/**
	 * 插入实体;
	 * 
	 * @param entity
	 */
	public void insert(Entity entity);

	/**
	 * 删除实体;
	 * 
	 * @param entity
	 */
	public void delete(Entity entity);

	/**
	 * 更新实体;
	 * 
	 * @param entity
	 */
	public void update(Entity entity);

	/**
	 * 根据id查询指定类型的实体;
	 * 
	 * @param entityClass
	 * @param id
	 * @return
	 */
	public <T extends Entity> T get(Class<T> entityClass, Serializable id);

	public <T> List<T> query(Class<T> entityClass, String[] params, Object[] values);

	/**
	 * FIXME: crazyjohn 泛华然后去掉
	 * 
	 * @param entityClass
	 * @return
	 * @throws Exception
	 */
	public long getMaxEntityId(Class<?> entityClass) throws Exception;

}
