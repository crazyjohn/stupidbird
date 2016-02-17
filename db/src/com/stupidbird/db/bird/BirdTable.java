package com.stupidbird.db.bird;

import java.io.Serializable;
import java.util.List;
import java.util.TreeMap;

public class BirdTable implements Table {
	protected TreeMap<Serializable, String> records = new TreeMap<Serializable, String>();
	protected Class<?> tableClass;
	protected long maxId;

	public BirdTable(Class<?> tableClass) {
		this.tableClass = tableClass;
	}

	@Override
	public Class<?> getTableClass() {
		return tableClass;
	}

	@Override
	public String get(Serializable id) {
		return records.get(id);
	}

	@Override
	public void update(Serializable id, String record) {
		this.records.replace(id, record);
	}

	@Override
	public void delete(Serializable id) {
		this.records.remove(id);
	}

	@Override
	public void insert(Serializable id, String record) {
		this.records.put(id, record);
	}

	@Override
	public <T> List<T> query(String[] params, Object[] values) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> List<T> limit(int from, int to) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long maxId() {
		return maxId;
	}
}
