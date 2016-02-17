package com.stupidbird.db.uuid;

import java.util.concurrent.atomic.AtomicLong;

import com.stupidbird.core.annotation.concurrency.ThreadSafeUnit;

/**
 * The UUID64 struct;
 * <P>
 * Total 63 bits = regionId (5 bits) + serverId (10 bits) + objectId (45 bits) +
 * other (3 bits)
 * 
 * @author crazyjohn
 *
 */
@ThreadSafeUnit
public class UUID64 implements UUID {
	// constants
	private static final int TOTAL_BITS = 63;
	private static final int REGION_BITS = 5;
	private static final int SERVER_BITS = 10;
	private static final int OBJECT_BITS = 45;
	public static final int OTHER_BITS = TOTAL_BITS - REGION_BITS - SERVER_BITS - OBJECT_BITS;
	// bits
	private int regionBits;
	private int serverBits;
	protected int objectBits;
	// ids
	private int regionId;

	private int serverId;
	// masks
	private long regionIdMask;
	private long serverIdMask;
	// generator
	private AtomicLong idGenerator;
	private long maxValue;

	public UUID64(int regionBits, int serverBits, int objectBits, int regionId, int serverId, long initValue) {
		this.regionBits = regionBits;
		this.serverBits = serverBits;
		this.objectBits = objectBits;
		this.regionId = regionId;
		this.serverId = serverId;
		// checkValue
		checkValue(regionId, regionBits);
		checkValue(serverId, serverBits);
		// high and mask
		long high = computeLeftMoveValue(regionId, (TOTAL_BITS - regionBits));
		this.regionIdMask = getMaskValue(regionBits, TOTAL_BITS - regionBits);
		high = high | (computeLeftMoveValue(serverId, (TOTAL_BITS - regionBits - serverBits)));
		this.serverIdMask = getMaskValue(serverBits, TOTAL_BITS - regionBits - serverBits);
		// max value
		maxValue = Long.MAX_VALUE;
		// id
		this.idGenerator = new AtomicLong(high | initValue);
	}

	private long computeLeftMoveValue(long value, int bits) {
		return value << bits;
	}

	/**
	 * Build the default UUID;
	 * 
	 * @param regionId
	 * @param serverId
	 * @return
	 */
	public static UUID buildDefaultUUID(int regionId, int serverId, long initValue) {
		UUID uuid = new UUID64(REGION_BITS, SERVER_BITS, OBJECT_BITS, regionId, serverId, initValue);
		return uuid;
	}

	private long getMaskValue(int bits, int shiftBits) {
		long maxValue = getMaxValue(bits);
		return maxValue << shiftBits;
	}

	/**
	 * Check if the value up to the max;
	 * 
	 * @param value
	 * @param bits
	 * 
	 * @throws IllegalArgumentException
	 *             when the value up to the max;
	 */
	private void checkValue(int value, int bits) {
		long maxValue = getMaxValue(bits);
		if (value > maxValue) {
			throw new IllegalArgumentException(String.format("The max value is: %d, you fucker's value is: %d", maxValue, value));
		}
	}

	/**
	 * Get the max value;
	 * 
	 * @param bits
	 * @return
	 */
	private long getMaxValue(int bits) {
		return (1 << bits) - 1;
	}

	@Override
	public long getNextId() {
		long nextId = this.idGenerator.incrementAndGet();
		if (nextId > maxValue) {
			this.idGenerator.set(maxValue);
			throw new IllegalStateException(String.format("Id up to the max value: %d", nextId));
		}
		return idGenerator.get();
	}

	@Override
	public long getCurrentId() {
		return idGenerator.get();
	}

	@Override
	public int getRegionId(long uuid) {
		return (int) ((this.regionIdMask & uuid) >> (TOTAL_BITS - this.regionBits));
	}

	@Override
	public int getServerId(long uuid) {
		return (int) ((this.serverIdMask & uuid) >> (TOTAL_BITS - this.regionBits - this.serverBits));
	}

	public int getRegionId() {
		return regionId;
	}

	public int getServerId() {
		return serverId;
	}

}
