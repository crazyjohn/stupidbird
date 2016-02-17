package com.stupidbird.game.entity;

import java.util.ArrayList;
import java.util.List;

import com.stupidbird.db.entity.BaseTableMetadataEntity;

/**
 * 角色实体;
 * 
 * @author crazyjohn
 *
 */
public class HumanEntity extends BaseTableMetadataEntity {
	private long id;
	private long playerId;
	private String name;
	private int level;
	private List<HumanItemEntity> items = new ArrayList<HumanItemEntity>();
	private List<HumanEquipEntity> equips = new ArrayList<HumanEquipEntity>();

	public long getPlayerId() {
		return playerId;
	}

	public void setPlayerId(long playerId) {
		this.playerId = playerId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public void setId(long id) {
		this.id = id;
	}

	@Override
	public long getId() {
		return id;
	}

	@Override
	public String getIdName() {
		return "id";
	}

	@Override
	public String getTableName() {
		return "human";
	}

	@Override
	public Object[] values() {
		return new Object[] { getId(), getPlayerId(), getName(), getLevel() };
	}

	@Override
	public String[] params() {
		return new String[] { "id", "playerId", "name", "level" };
	}

	public List<HumanItemEntity> getItems() {
		return items;
	}

	public void setItems(List<HumanItemEntity> items) {
		this.items = items;
	}

	public void motherGive() {
		// mock
		HumanItemEntity itemEntity = new HumanItemEntity();
		itemEntity.setCount(1);
		itemEntity.setId(888);
		this.items.add(itemEntity);
		// equip
		HumanEquipEntity equipEntity = new HumanEquipEntity();
		equipEntity.setId(999);
		equipEntity.setCount(2);
		this.equips.add(equipEntity);
	}

	public List<HumanEquipEntity> getEquips() {
		return equips;
	}

	public void setEquips(List<HumanEquipEntity> equips) {
		this.equips = equips;
	}
}
