package com.stupidbird.core.cryption;

import java.security.MessageDigest;

import com.stupidbird.core.os.GameMonitor;
import com.stupidbird.core.os.GameOSOperator;

/**
 * SHA加密封装
 * 
 * @author stupidbird
 */
public class SHACrypt {
	/**
	 * 加密字节数组
	 * 
	 * @param bytes
	 * @param offset
	 * @param size
	 * @return
	 * @throws Exception
	 */
	public static byte[] encrypt(byte[] bytes, int offset, int size) throws Exception {
		MessageDigest messageDigest = MessageDigest.getInstance("SHA");
		messageDigest.update(bytes, offset, size);
		return messageDigest.digest();
	}

	/**
	 * 加密字节数组, 返回字符串
	 * 
	 * @param bytes
	 * @return
	 */
	public static String encrypt(byte[] bytes) {
		try {
			byte[] shaBytes = encrypt(bytes, 0, bytes.length);
			return GameOSOperator.bytesToHexString(shaBytes);
		} catch (Exception e) {
			GameMonitor.catchException(e);
		}
		return null;
	}
}
