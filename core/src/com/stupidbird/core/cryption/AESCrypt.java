package com.stupidbird.core.cryption;

import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import com.stupidbird.core.os.GameMonitor;

/**
 * AES加密算法封装
 * 
 * @author stupidbird
 */
public class AESCrypt {
	/**
	 * 加密对象
	 */
	Cipher cipher;
	/**
	 * 密钥
	 */
	KeyGenerator keyGenerator;
	/**
	 * 密钥长度
	 */
	static final int KEY_LENGTH = 128;

	/**
	 * 初始化, 秘钥长度128
	 * 
	 * @param secretKey
	 * @param encrypt
	 * @return
	 */
	public boolean init(byte[] secretKey, boolean encrypt) {
		try {
			if (secretKey != null) {
				if (secretKey.length > KEY_LENGTH) {
					return false;
				}
				keyGenerator = KeyGenerator.getInstance("AES");
				keyGenerator.init(KEY_LENGTH, new SecureRandom(secretKey));
				SecretKey key = keyGenerator.generateKey();
				cipher = Cipher.getInstance("AES");
				cipher.init(encrypt ? Cipher.ENCRYPT_MODE : Cipher.DECRYPT_MODE, key);
				return true;
			}
		} catch (Exception e) {
			GameMonitor.catchException(e);
		}
		return false;
	}

	/**
	 * 加解密
	 * 
	 * @param input
	 * @return
	 */
	public byte[] digit(byte[] input) {
		try {
			byte[] output = cipher.doFinal(input);
			return output;
		} catch (Exception e) {
			GameMonitor.catchException(e);
		}
		return null;
	}
}
