package com.stupidbird.core.cryption;

import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.crypto.Cipher;

import com.stupidbird.core.os.GameMonitor;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * RSA加密算法封装
 * 
 * @author stupidbird
 */
public class RsaCrypt {
	/**
	 * 生成密钥对
	 * 
	 * ·param keySize: 512, 1024
	 * @return
	 */
	public static Map<String, String> generateKeyPair(int keySize) {
		try {
			Map<String, String> map = new HashMap<String, String>();
			KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");
			// 密钥位数
			keyPairGen.initialize(keySize);
			// 密钥对
			KeyPair keyPair = keyPairGen.generateKeyPair();
			// 公钥
			PublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
			// 私钥
			PrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
			// 得到公钥字符串
			String publicKeyString = getKeyString(publicKey);
			// 得到私钥字符串
			String privateKeyString = getKeyString(privateKey);
			// 将生成的密钥对返回
			map.put("publicKey", publicKeyString);
			map.put("privateKey", privateKeyString);
			return map;
		} catch (Exception e) {
			GameMonitor.catchException(e);
		}
		return null;
	}

	/**
	 * 获得公钥的Key
	 * 
	 * @param key
	 * @return
	 */
	public static PublicKey getPublicKey(String key) {
		try {
			byte[] keyBytes;
			keyBytes = (new BASE64Decoder()).decodeBuffer(key);
			X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			PublicKey publicKey = keyFactory.generatePublic(keySpec);
			return publicKey;
		} catch (Exception e) {
			GameMonitor.catchException(e);
			return null;
		}
	}

	/**
	 * 使用模和指数生成RSA私钥 注意：【此代码用了默认补位方式，为RSA/None/PKCS1Padding，不同JDK默认的补位方式可能不同，如Android默认是RSA /None/NoPadding】
	 * 
	 * @return
	 */
	public static RSAPrivateKey getPrivateKey(String key) {
		try {
			byte[] keyBytes;
			keyBytes = (new BASE64Decoder()).decodeBuffer(key);
			PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			PrivateKey privateKey = keyFactory.generatePrivate(keySpec);
			return (RSAPrivateKey) privateKey;
		} catch (Exception e) {
			GameMonitor.catchException(e);
			return null;
		}
	}

	/**
	 * 公钥加密
	 * 
	 * @param data
	 * @param publicKey
	 * @return
	 * @throws Exception
	 */
	public static String encryptByPublicKey(String data, RSAPublicKey publicKey) throws Exception {
		Cipher cipher = Cipher.getInstance("RSA");
		cipher.init(Cipher.ENCRYPT_MODE, publicKey);
		// 模长
		int key_len = publicKey.getModulus().bitLength() / 8;
		// 加密数据长度 <= 模长-11
		String[] datas = splitString(data, key_len - 11);
		String mi = "";
		// 如果明文长度大于模长-11则要分组加密
		for (String s : datas) {
			mi += bcd2Str(cipher.doFinal(s.getBytes()));
		}
		return mi;
	}

	/**
	 * 私钥解密
	 * 
	 * @param data
	 * @param privateKey
	 * @return
	 * @throws Exception
	 */
	public static byte[] decryptByPrivateKey(byte[] data, RSAPrivateKey privateKey) throws Exception {
		Cipher cipher = Cipher.getInstance("RSA");
		cipher.init(Cipher.DECRYPT_MODE, privateKey);
		// 模长
		int key_len = privateKey.getModulus().bitLength() / 8;
		byte[] bcd = ASCII_To_BCD(data, data.length);
		System.err.println(bcd.length);
		// 如果密文长度大于模长则要分组解密
		byte[][] arrays = splitArray(bcd, key_len);
		List<byte[]> bytesList = new LinkedList<>();
		int byteLength = 0;
		for (byte[] arr : arrays) {
			byte[] value = cipher.doFinal(arr);
			bytesList.add(value);
			byteLength += value.length;
		}
		byte[] result = new byte[byteLength];
		int resultIndex = 0;
		for (byte[] arr : bytesList) {
			System.arraycopy(arr, 0, result, resultIndex, arr.length);
			resultIndex += arr.length;
		}
		return result;
	}

	/**
	 * ASCII码转BCD码
	 */
	private static byte[] ASCII_To_BCD(byte[] ascii, int asc_len) {
		byte[] bcd = new byte[asc_len / 2];
		int j = 0;
		for (int i = 0; i < (asc_len + 1) / 2; i++) {
			bcd[i] = asc_to_bcd(ascii[j++]);
			bcd[i] = (byte) (((j >= asc_len) ? 0x00 : asc_to_bcd(ascii[j++])) + (bcd[i] << 4));
		}
		return bcd;
	}

	private static byte asc_to_bcd(byte asc) {
		byte bcd;

		if ((asc >= '0') && (asc <= '9'))
			bcd = (byte) (asc - '0');
		else if ((asc >= 'A') && (asc <= 'F'))
			bcd = (byte) (asc - 'A' + 10);
		else if ((asc >= 'a') && (asc <= 'f'))
			bcd = (byte) (asc - 'a' + 10);
		else
			bcd = (byte) (asc - 48);
		return bcd;
	}

	/**
	 * BCD转字符串
	 */
	private static String bcd2Str(byte[] bytes) {
		char temp[] = new char[bytes.length * 2], val;

		for (int i = 0; i < bytes.length; i++) {
			val = (char) (((bytes[i] & 0xf0) >> 4) & 0x0f);
			temp[i * 2] = (char) (val > 9 ? val + 'A' - 10 : val + '0');

			val = (char) (bytes[i] & 0x0f);
			temp[i * 2 + 1] = (char) (val > 9 ? val + 'A' - 10 : val + '0');
		}
		return new String(temp);
	}

	/**
	 * 拆分字符串
	 */
	private static String[] splitString(String string, int len) {
		int x = string.length() / len;
		int y = string.length() % len;
		int z = 0;
		if (y != 0) {
			z = 1;
		}
		String[] strings = new String[x + z];
		String str = "";
		for (int i = 0; i < x + z; i++) {
			if (i == x + z - 1 && y != 0) {
				str = string.substring(i * len, i * len + y);
			} else {
				str = string.substring(i * len, i * len + len);
			}
			strings[i] = str;
		}
		return strings;
	}

	/**
	 * 拆分数组
	 */
	private static byte[][] splitArray(byte[] data, int len) {
		int x = data.length / len;
		int y = data.length % len;
		int z = 0;
		if (y != 0) {
			z = 1;
		}
		byte[][] arrays = new byte[x + z][];
		byte[] arr;
		for (int i = 0; i < x + z; i++) {
			arr = new byte[len];
			if (i == x + z - 1 && y != 0) {
				System.arraycopy(data, i * len, arr, 0, y);
			} else {
				System.arraycopy(data, i * len, arr, 0, len);
			}
			arrays[i] = arr;
		}
		return arrays;
	}

	/**
	 * 得到密钥字符串（经过base64编码）
	 * 
	 * @return
	 */
	private static String getKeyString(Key key) throws Exception {
		byte[] keyBytes = key.getEncoded();
		return (new BASE64Encoder()).encode(keyBytes);
	}
}
