package com.stupidbird.game.entity;

import com.stupidbird.db.entity.BaseTableMetadataEntity;

/**
 * 角色装备实体;
 * 
 * @author crazyjohn
 *
 */
public class HumanEquipEntity extends BaseTableMetadataEntity {
	private long id;
	private int templateId;
	private int count;

	public int getTemplateId() {
		return templateId;
	}

	public void setTemplateId(int templateId) {
		this.templateId = templateId;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public void setId(long id) {
		this.id = id;
	}

	@Override
	public long getId() {
		return id;
	}

}
