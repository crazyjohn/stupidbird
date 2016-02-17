package com.stupidbird.db.entity;

public interface Binaryable {

	public byte[] toByteArray();
	
	public void mergeFrom(byte[] bytes);
}
