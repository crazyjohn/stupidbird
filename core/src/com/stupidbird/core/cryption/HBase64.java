package com.stupidbird.core.cryption;

import com.stupidbird.core.os.GameMonitor;

import sun.misc.BASE64Decoder;

/**
 * base64加解密接口封装
 * 
 * @author stupidbird
 * 
 */
public class HBase64 {
	/**
	 * 编码表
	 */
	public static char[] BASE64_DIGITS = { 
		'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 
		'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', 
		'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '+', '/' };

	/**
	 * Base64加密
	 * 
	 * @param bytes
	 * @return
	 */
	public static String encode(byte[] bytes) {
		return encode(bytes, 0, bytes.length);
	}

	/**
	 * Base64加密
	 * 
	 * @param bytes
	 * @return
	 */
	public static String encode(byte[] bytes, int offset, int length) {
		int totalBits = length * 8;
		int curPos = offset;
		StringBuffer base64 = new StringBuffer();
		while (curPos < totalBits) {
			int bytePos = curPos / 8;
			switch (curPos % 8) {
			case 0:
				base64.append(BASE64_DIGITS[(bytes[bytePos] & 0xfc) >> 2]);
				break;
				
			case 2:
				base64.append(BASE64_DIGITS[(bytes[bytePos] & 0x3f)]);
				break;
				
			case 4:
				if (bytePos == bytes.length - 1) {
					base64.append(BASE64_DIGITS[((bytes[bytePos] & 0x0f) << 2) & 0x3f]);
				} else {
					int pos = (((bytes[bytePos] & 0x0f) << 2) | ((bytes[bytePos + 1] & 0xc0) >> 6)) & 0x3f;
					base64.append(BASE64_DIGITS[pos]);
				}
				break;
				
			case 6:
				if (bytePos == bytes.length - 1) {
					base64.append(BASE64_DIGITS[((bytes[bytePos] & 0x03) << 4) & 0x3f]);
				} else {
					int pos = (((bytes[bytePos] & 0x03) << 4) | ((bytes[bytePos + 1] & 0xf0) >> 4)) & 0x3f;
					base64.append(BASE64_DIGITS[pos]);
				}
				break;
				
			default:
				break;
			}
			curPos += 6;
		}

		if (totalBits % 6 == 2) {
			base64.append("==");
		} else if (totalBits % 6 == 4) {
			base64.append("=");
		}
		return base64.toString();
	}

	/**
	 * Base64解密
	 * 
	 * @param str
	 * @return
	 */
	public static byte[] decode(String info) {
		BASE64Decoder decoder = new BASE64Decoder();
		try {
			return decoder.decodeBuffer(info);
		} catch (Exception e) {
			GameMonitor.catchException(e);
		}
		return null;
	}
}
