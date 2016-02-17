package com.stupidbird.db.uuid;

/**
 * The UUID;
 * 
 * @author crazyjohn
 *
 */
public interface UUID {

	/**
	 * Get next id;
	 * 
	 * @return
	 */
	public long getNextId();

	/**
	 * Get current id;
	 * 
	 * @return
	 */
	public long getCurrentId();

	/**
	 * Get the regionId;
	 * 
	 * @param uuid
	 * @return
	 */
	public int getRegionId(long uuid);

	/**
	 * Get the serverId;
	 * 
	 * @param uuid
	 * @return
	 */
	public int getServerId(long uuid);
}
