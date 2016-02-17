package com.stupidbird.db.mongo;

import org.bson.types.ObjectId;

import com.stupidbird.db.entity.Entity;

public interface MongoEntity extends Entity{
	
	public ObjectId getObjectId();

	public void setObjectId(ObjectId id);
}
