package com.stupidbird.game.entity;

import com.stupidbird.db.entity.BaseTableMetadataEntity;

/**
 * 玩家实体;
 * <p>
 * FIXME: crazyjohn 思考通过中间语言DSL来自动生成实体的类文件和db(mysql;cassandra)的描述文件
 * 
 * @author crazyjohn
 *
 */
public class PlayerEntity extends BaseTableMetadataEntity {
	private long id;
	private String puid;

	public String getPuid() {
		return puid;
	}

	public void setPuid(String puid) {
		this.puid = puid;
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
		return "player";
	}

	@Override
	public Object[] values() {
		return new Object[] { getId(), getPuid() };
	}

	@Override
	public String[] params() {
		return new String[] { "id", "puid" };
	}

}
