package com.stupidbird.db.uuid;

import java.util.HashMap;
import java.util.Map;

/**
 * The uuid type;
 * 
 * @author crazyjohn
 *
 */
public enum UUIDType {
	/** Player */
	PLAYER(0),
	/** Human */
	HUMAN(1), ;

	private final int type;
	private static Map<Integer, UUIDType> types = new HashMap<Integer, UUIDType>();

	static {
		for (UUIDType eachType : UUIDType.values()) {
			types.put(eachType.getType(), eachType);
		}
	}

	private UUIDType(int type) {
		this.type = type;
	}

	public int getType() {
		return this.type;
	}

	public static UUIDType typeOf(int type) {
		return types.get(type);
	}
}
