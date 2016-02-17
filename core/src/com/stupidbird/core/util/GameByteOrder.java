package com.stupidbird.core.util;

/**
 * 字节序转换
 * 
 * @author stupidbird
 */
public class GameByteOrder {
	/**
	 * 小端字节序字节数组
	 * 
	 * @param value
	 * @return
	 */
	public static byte[] littleEndianBytes(int value) {
		byte[] bytes = new byte[4];
		bytes[0] = (byte) (value & 0xff);
		bytes[1] = (byte) (value >> 8 & 0xff);
		bytes[2] = (byte) (value >> 16 & 0xff);
		bytes[3] = (byte) (value >> 24 & 0xff);
		return bytes;
	}

	/**
	 * 大端字节序字节数组
	 * 
	 * @param value
	 * @return
	 */
	public static byte[] bigEndianBytes(int value) {
		byte[] bytes = new byte[4];
		bytes[3] = (byte) (value & 0xff);
		bytes[2] = (byte) (value >> 8 & 0xff);
		bytes[1] = (byte) (value >> 16 & 0xff);
		bytes[0] = (byte) (value >> 24 & 0xff);
		return bytes;
	}

	/**
	 * 小端字节序字节数组
	 * 
	 * @param value
	 * @return
	 */
	public static byte[] littleEndianBytes(short value) {
		byte[] bytes = new byte[2];
		bytes[0] = (byte) (value & 0xff);
		bytes[1] = (byte) (value >> 8 & 0xff);
		return bytes;
	}

	/**
	 * 大端字节序字节数组
	 * 
	 * @param value
	 * @return
	 */
	public static byte[] bigEndianBytes(short value) {
		byte[] bytes = new byte[2];
		bytes[1] = (byte) (value & 0xff);
		bytes[0] = (byte) (value >> 8 & 0xff);
		return bytes;
	}

	/**
	 * 位操作前转换
	 * @param value
	 * @return
	 */
	private static int prepareForBit(int value) {
		return value >= 0 ? value : value + 256;
	}
	
	/**
	 * 大端字节序字节数组转换为整数
	 * 
	 * @param bytes
	 * @return
	 */
	public static int bigEndianBytesToInt(byte[] bytes) {
		return (prepareForBit(bytes[0]) << 24) | 
			   (prepareForBit(bytes[1]) << 16) | 
			   (prepareForBit(bytes[2]) << 8) | 
			   (prepareForBit(bytes[3]));
	}

	/**
	 * 小端字节序字节数组转换为整数
	 * 
	 * @param bytes
	 * @return
	 */
	public static int littleEndianBytesToInt(byte[] bytes) {
		return (prepareForBit(bytes[3]) << 24) | 
			   (prepareForBit(bytes[2]) << 16) | 
			   (prepareForBit(bytes[1]) << 8) | 
			   (prepareForBit(bytes[0]));
	}

	/**
	 * 大端字节序字节数组转换为整数
	 * 
	 * @param bytes
	 * @return
	 */
	public static short bigEndianBytesToShort(byte[] bytes) {
		return (short) ((prepareForBit(bytes[0]) << 8) | (prepareForBit(bytes[1])));
	}

	/**
	 * 小端字节序字节数组转换为整数
	 * 
	 * @param bytes
	 * @return
	 */
	public static short littleEndianBytesToShort(byte[] bytes) {
		return (short) ((prepareForBit(bytes[1]) << 8) | (prepareForBit(bytes[0])));
	}

	/**
	 * 字节反转
	 * 
	 * @param value
	 * @return
	 */
	public static short reverseShort(short value) {
		return bigEndianBytesToShort(littleEndianBytes(value));
	}

	/**
	 * 字节反转
	 * 
	 * @param value
	 * @return
	 */
	public static int reverseInt(int value) {
		return bigEndianBytesToShort(littleEndianBytes(value));
	}
}
