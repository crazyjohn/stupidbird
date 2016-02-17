package com.stupidbird.game.entity;

import com.stupidbird.db.entity.BaseTableMetadataEntity;

/**
 * 角色物品实体;
 * 
 * @author crazyjohn
 *
 */
public class HumanItemEntity extends BaseTableMetadataEntity {
	private long id;
	private int count;
	private int templateId;

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public int getTemplateId() {
		return templateId;
	}

	public void setTemplateId(int templateId) {
		this.templateId = templateId;
	}

	public void setId(long id) {
		this.id = id;
	}

	@Override
	public long getId() {
		return id;
	}

}
