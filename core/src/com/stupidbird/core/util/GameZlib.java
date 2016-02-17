package com.stupidbird.core.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

import com.stupidbird.core.os.GameMonitor;

/**
 * zlib压缩&解压缩
 * 
 * @author stupidbird
 */
public class GameZlib {
	/**
	 * 计算压缩后最大字节数
	 * 
	 * @param sourceLen
	 * @return
	 */
	public static int zlibBound(int sourceLen) {
		return sourceLen + (sourceLen >> 12) + (sourceLen >> 14) + (sourceLen >> 25) + 13;
	}

	/**
	 * 压缩
	 * 
	 * @param inputData
	 * @return
	 */
	public static byte[] zlibDeflate(byte[] inputData) {
		return zlibDeflate(inputData, 0, inputData.length);
	}

	/**
	 * zlib压缩
	 * 
	 * @param inputData
	 * @param offset
	 * @param length
	 * @return
	 */
	public static byte[] zlibDeflate(byte[] inputData, int offset, int length) {
		try {
			Deflater deflater = new Deflater();
			deflater.setInput(inputData, offset, length);
			deflater.finish();

			ByteArrayOutputStream bos = new ByteArrayOutputStream(inputData.length);
			byte[] buf = new byte[1024];
			while (!deflater.finished()) {
				int count = deflater.deflate(buf);
				bos.write(buf, 0, count);
			}
			deflater.end();
			bos.close();
			return bos.toByteArray();
		} catch (IOException e) {
			GameMonitor.catchException(e);
		}
		return null;
	}
	
	/**
	 * 解压缩
	 * 
	 * @param inputData
	 * @return
	 */
	public static byte[] zlibInflate(byte[] inputData) {
		return zlibInflate(inputData, 0, inputData.length);
	}
	
	/**
	 * 解压缩
	 * 
	 * @param inputData
	 * @return
	 */
	public static byte[] zlibInflate(byte[] inputData, int offset, int length) {
		try {
			Inflater inflater = new Inflater();
			inflater.setInput(inputData, offset, length);

			ByteArrayOutputStream bos = new ByteArrayOutputStream(length);
			byte[] buf = new byte[1024];
			while (!inflater.finished()) {
				int count = inflater.inflate(buf);
				bos.write(buf, 0, count);
			}
			inflater.end();
			bos.close();
			return bos.toByteArray();
		} catch (Exception e) {
			GameMonitor.catchException(e);
		}
		return null;
	}
}
