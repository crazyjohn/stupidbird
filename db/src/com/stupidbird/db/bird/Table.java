package com.stupidbird.db.bird;

import java.io.Serializable;
import java.util.List;

public interface Table {

	public Class<?> getTableClass();

	public String get(Serializable id);

	public void update(Serializable id, String record);

	public void delete(Serializable id);

	public void insert(Serializable id, String record);

	public <T> List<T> query(String[] params, Object[] values);

	public <T> List<T> limit(int from, int to);

	public long maxId();
}
