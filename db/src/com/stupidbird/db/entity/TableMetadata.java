package com.stupidbird.db.entity;

import java.util.Map;

/**
 * 表元数据实体接口;
 * 
 * @author crazyjohn
 *
 */
public interface TableMetadata {

	public String getIdName();

	public String getTableName();

	public Object[] values();

	public String[] params();

	public Map<String, Object> modifiedInfos();
}
