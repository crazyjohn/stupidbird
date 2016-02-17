package com.stupidbird.core.cryption;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 使用异或算法实现的简单加解密处理器，只对文件的前{@link #BYTES_LIMIT}个字节进行加解密
 * 
 */
public class EncryptUtils {

	/** 加密字节数上限 */
	private final static int BYTES_LIMIT = 100;

	public static int[] seed = { 0xD83EFCAB, 0xB34CABE6, 0xF74ECAB8,
			0xCABBC2A3, 0xBACF0CD9, 0xDFBB07F9, 0xC66FACB8, 0xB1F9DE96,
			0xAB95CCAB };

	/**
	 * 加密
	 * 
	 * @param orgFile
	 * @param newFile
	 */
	public static byte[] encryptBytes(byte[] orgBytes) {
		DataInputStream dataInputStream = new DataInputStream(
				new ByteArrayInputStream(orgBytes));
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		DataOutputStream dataOutputStream = new DataOutputStream(out);
		try {
			encryptStream(dataInputStream, dataOutputStream);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		return out.toByteArray();
	}

	public static byte[] decryptBytes(byte[] orgBytes) {
		return encryptBytes(orgBytes);
	}

	/**
	 * 加密文件;
	 * 
	 * @param orgFile
	 * @param newFile
	 */
	public static void encryptFile(File orgFile, File newFile) {
		try {
			FileInputStream in = new FileInputStream(orgFile.getAbsoluteFile());
			DataInputStream dataInputStream = new DataInputStream(in);

			ByteArrayOutputStream out = new ByteArrayOutputStream();
			DataOutputStream dataOutputStream = new DataOutputStream(out);

			try {
				encryptStream(dataInputStream, dataOutputStream);
				DataOutputStream d = new DataOutputStream(
						new BufferedOutputStream(new FileOutputStream(newFile)));
				out.writeTo(d);
				d.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			try {
				dataOutputStream.close();
				out.close();
				dataInputStream.close();
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @param dataInputStream
	 * @param dataOutputStream
	 * @throws IOException
	 */
	private static void encryptStream(DataInputStream dataInputStream,
			DataOutputStream dataOutputStream) throws IOException {
		int readTimes = 0;
		int limitTimes = dataInputStream.available() > BYTES_LIMIT ? BYTES_LIMIT / 4
				: dataInputStream.available();
		while (dataInputStream.available() > 4 && readTimes < limitTimes) {
			dataOutputStream.writeInt(dataInputStream.readInt()
					^ seed[readTimes % 9]);
			readTimes++;
		}
		if (dataInputStream.available() > 0) {
			byte[] leaveBytes = new byte[dataInputStream.available()];
			dataInputStream.read(leaveBytes);
			dataOutputStream.write(leaveBytes);
		}
	}
	
	private static final String ALGORITHM_MD5="MD5"; 
	
	public static byte[] encryptMD5(byte[] data) {
		MessageDigest md5 = null;
		try {
			md5 = MessageDigest.getInstance(ALGORITHM_MD5);
		} catch (NoSuchAlgorithmException e) {			
			e.printStackTrace();
		}
		// editby: crazyjohn not null check
		if (md5 == null) {
			return new byte[0];
		}
		md5.update(data); 
		return md5.digest();
	}
	
	public static String generateMD5String(byte[] data){
		byte[] bytes = encryptMD5(data);
		int i;
		StringBuffer buf = new StringBuffer("");
		for (int offset = 0; offset < bytes.length; offset++) {
			i = bytes[offset];			
			if (i < 0)
				i += 256;
			if (i < 16)
				buf.append("=");
			buf.append(Integer.toHexString(i));
		}
		return buf.toString();
	}

	public static void main(String[] args) {
		String s = "gmHiFun0408";		
		byte[] bytes = s.getBytes();
		byte[] resultBytes = decryptBytes(encryptBytes(bytes));
		System.out.println(new String(resultBytes));
		System.out.println(generateMD5String(bytes));
		System.out.println(generateMD5String(encryptBytes(bytes)));
	}
}
