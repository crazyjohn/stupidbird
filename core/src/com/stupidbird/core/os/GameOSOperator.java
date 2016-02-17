package com.stupidbird.core.os;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.lang.management.ManagementFactory;
import java.lang.reflect.Field;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import net.sf.json.JSONObject;

import com.stupidbird.core.log.GameLog;
import com.sun.net.httpserver.HttpExchange;

/**
 * 系统相关操作封装
 * 
 * @author stupidbird
 * 
 */
public class GameOSOperator {
	/**
	 * 加密字符串字符定义
	 */
	public static final char HEX_DIGITS[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd',
			'e', 'f' };

	/**
	 * 计算crc校验码, AP Hash算法
	 * 
	 * @param bytes
	 * @param offset
	 * @param size
	 * @param crc
	 * @return
	 */
	public static int calcCrc(byte[] bytes, int offset, int size, int crc) {
		int hash = crc;
		for (int i = offset; i < offset + size; i++) {
			hash ^= ((i & 1) == 0) ? ((hash << 7) ^ (bytes[i] & 0xff) ^ (hash >>> 3)) : (~((hash << 11)
					^ (bytes[i] & 0xff) ^ (hash >>> 5)));
		}
		return hash;
	}

	/**
	 * 计算crc校验码
	 * 
	 * @param bytes
	 * @param crc
	 * @return
	 */
	public static int calcCrc(byte[] bytes, int crc) {
		return calcCrc(bytes, 0, bytes.length, crc);
	}

	/**
	 * 计算crc校验码
	 * 
	 * @param bytes
	 * @return
	 */
	public static int calcCrc(byte[] bytes) {
		return calcCrc(bytes, 0);
	}

	/**
	 * AP Hash算法(和calcCrc一致)
	 * 
	 * @param string
	 * @return
	 */
	public static int hashString(String string) {
		int hash = 0;
		for (int i = 0; i < string.length(); i++) {
			hash ^= ((i & 1) == 0) ? ((hash << 7) ^ string.charAt(i) ^ (hash >> 3)) : (~((hash << 11)
					^ string.charAt(i) ^ (hash >> 5)));
		}
		return (hash & 0x7FFFFFFF);
	}

	/**
	 * 字节数组转换为16进制字符串
	 * 
	 * @param data
	 * @return
	 */
	public static String bytesToHexString(byte[] data) {
		StringBuilder builder = new StringBuilder(data.length * 4);
		for (int i = 0; i < data.length; i++) {
			if (i > 0 && i % 16 == 0) {
				builder.append('\n');
			}
			builder.append(HEX_DIGITS[data[i] >>> 4 & 0xF]);
			builder.append(HEX_DIGITS[data[i] & 0xF]);
			builder.append(' ');
		}
		return builder.toString();
	}

	/**
	 * 字节数组转换为16进制字符串
	 * 
	 * @param data
	 * @return
	 */
	public static String bytesToHexString(byte[] data, int offset, int size) {
		StringBuilder builder = new StringBuilder(size * 4);
		for (int i = offset; i < offset + size; i++) {
			if (i > 0 && i % 16 == 0) {
				builder.append('\n');
			}
			builder.append(HEX_DIGITS[data[i] >>> 4 & 0xF]);
			builder.append(HEX_DIGITS[data[i] & 0xF]);
			builder.append(' ');
		}
		return builder.toString();
	}

	/**
	 * 十六进制字符转换为字节
	 * 
	 * @param ch
	 * @return
	 */
	public static byte hexChar2Byte(char ch) {
		if (ch >= '0' && ch <= '9') {
			return (byte) (ch - '0');
		}
		return (byte) ((ch - 'a') + 10);
	}

	/**
	 * 十六进制字符串转换为字节数组
	 * 
	 * @param hex
	 * @return
	 */
	public static byte[] hex2Bytes(String hex) {
		hex = hex.toLowerCase().trim().replace(" ", "");
		int len = hex.length() / 2;
		byte[] bytes = new byte[len];
		for (int i = 0; i < len; i++) {
			bytes[i] = (byte) (hexChar2Byte(hex.charAt(i * 2)) * 16 + hexChar2Byte(hex.charAt(i * 2 + 1)));
		}
		return bytes;
	}

	/**
	 * 生成随机长度字符串
	 * 
	 * @param length
	 * @return
	 */
	public static String randomString(int length) {
		StringBuilder builder = new StringBuilder();
		try {
			for (int i = 0; i < length; i++) {
				char ch = 0;
				if (GameRand.randInt(0, 1) == 0) {
					ch = (char) GameRand.randInt('a', 'z');
				} else {
					ch = (char) GameRand.randInt('A', 'Z');
				}
				builder.append(ch);
			}
		} catch (Exception e) {
			GameMonitor.catchException(e);
		}
		return builder.toString();
	}

	/**
	 * 根据给定的字符序列生成随机长度字符串
	 * 
	 * @param length
	 * @return
	 */
	public static String randomString(int length, String charSequence) {
		if (charSequence == null || charSequence.equals("")) {
			charSequence = "abcdefghijklmnopqrstuvwxyz0123456789";
		}

		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < length; i++) {
			try {
				int index = GameRand.randInt(0, charSequence.length() - 1);
				builder.append(charSequence.charAt(index));
			} catch (Exception e) {
				GameMonitor.catchException(e);
			}
		}
		return builder.toString();
	}

	/**
	 * 浮点数转换为字符串保留小数点位数
	 * 
	 * @param value
	 * @param decimal
	 * @return
	 */
	public static String floatToString(float value, int decimal) {
		String fmtVal = String.valueOf(value);
		int pos = fmtVal.indexOf('.');
		if (pos + decimal < fmtVal.length()) {
			return fmtVal.substring(0, pos + decimal + 1);
		}
		return fmtVal;
	}

	/**
	 * 系统休眠
	 * 
	 * @param msTime
	 */
	public static void osSleep(long msTime) {
		try {
			Thread.sleep(msTime);
		} catch (Exception e) {
			GameMonitor.catchException(e);
		}
	}

	/**
	 * 默认sleep
	 */
	public static void sleep() {
		try {
			Thread.sleep(10);
		} catch (Exception e) {
			GameMonitor.catchException(e);
		}
	}

	/**
	 * 添加路径到库加载路径列表
	 * 
	 * @param path
	 * @throws IOException
	 */
	public static void addUsrPath(String path) {
		try {
			Field field = ClassLoader.class.getDeclaredField("usr_paths");
			field.setAccessible(true);
			String[] paths = (String[]) field.get(null);
			for (int i = 0; i < paths.length; i++) {
				if (path.equals(paths[i])) {
					return;
				}
			}

			String[] pathArray = new String[paths.length + 1];
			System.arraycopy(paths, 0, pathArray, 0, paths.length);
			pathArray[paths.length] = path;
			field.set(null, pathArray);
		} catch (Exception e) {
			GameMonitor.catchException(e);
		}
	}

	/**
	 * 获取当前进程ID
	 * 
	 * @return
	 */
	public static long getProcessId() {
		try {
			String processName = ManagementFactory.getRuntimeMXBean().getName();
			return Long.parseLong(processName.split("@")[0]);
		} catch (Exception e) {
			GameMonitor.catchException(e);
		}
		return 0;
	}

	/**
	 * 获取当前线程ID
	 * 
	 * @return
	 */
	public static long getThreadId() {
		long threadId = Thread.currentThread().getId();
		return threadId;
	}

	/**
	 * 打印系统环境
	 */
	public static void printOsEnv() {
		// 打印系统信息
		Properties props = System.getProperties();
		GameLog.logPrintln("Os: " + props.getProperty("os.name") + ", Arch: " + props.getProperty("os.arch")
				+ ", Version: " + props.getProperty("os.version"));

		// 用户路径
		String userDir = System.getProperty("user.dir");
		GameLog.logPrintln("UserDir: " + userDir);

		// 系统路径
		String homeDir = System.getProperty("java.home");
		GameLog.logPrintln("JavaHome: " + homeDir);
	}

	/**
	 * 判断windows系统
	 * 
	 * @return
	 */
	public static boolean isWindowsOS() {
		Properties props = System.getProperties();
		if (props.getProperty("os.name").toLowerCase().contains("windows")) {
			return true;
		}
		return false;
	}

	/**
	 * 从文件路径获取文件名
	 * 
	 * @param fullName
	 * @return
	 */
	public static String splitFileName(String fullName) {
		String name = "";
		fullName = fullName.replace("\\", "/");
		String[] items = fullName.split("/");
		if (items.length > 0) {
			name = items[items.length - 1];
		}
		return name;
	}

	/**
	 * 从文件路径获取文件路径
	 * 
	 * @param fullName
	 * @return
	 */
	public static String splitFilePath(String fullName) {
		String path = "";
		fullName = fullName.replace("\\", "/");
		String[] items = fullName.split("/");
		for (int i = 0; i < items.length - 1; i++) {
			path += items[i];
			path += "/";
		}
		return path;
	}

	/**
	 * 是否存在某文件
	 * 
	 * @param fileName
	 * @return
	 */
	public static boolean existFile(String fileName) {
		File file = new File(fileName);
		return file.exists() && file.isFile();
	}

	/**
	 * 是否存在某目录
	 * 
	 * @param folderName
	 * @return
	 */
	public static boolean existFolder(String folderName) {
		File file = new File(folderName);
		return file.exists() && file.isDirectory();
	}

	/**
	 * 获得文件大小
	 * 
	 * @param fileName
	 * @return
	 */
	public static long getFileSize(String fileName) {
		try {
			RandomAccessFile rafFile = new RandomAccessFile(fileName, "rb");
			long size = rafFile.length();
			rafFile.close();
			return size;
		} catch (Exception e) {
			GameMonitor.catchException(e);
		}
		return 0;
	}

	/**
	 * 删除文件
	 * 
	 * @param fileName
	 * @return
	 */
	public static boolean osDeleteFile(String fileName) {
		File file = new File(fileName);
		return file.delete();
	}

	/**
	 * 重命名文件
	 * 
	 * @param fileName
	 * @param newFileName
	 * @return
	 */
	public static boolean renameFile(String fileName, String newFileName) {
		File file = new File(fileName);
		return file.renameTo(new File(newFileName));
	}

	/**
	 * 设置文件可写
	 * 
	 * @param fileName
	 * @return
	 */
	public static boolean setFileWritable(String fileName) {
		File file = new File(fileName);
		return file.setWritable(true);
	}

	/**
	 * 创建目录树
	 * 
	 * @param dirName
	 * @return
	 */
	public static boolean createDir(String dirName) {
		if (dirName.length() <= 0)
			return true;

		String sysDir = dirName.replace("\\", "/");
		File file = new File(sysDir);
		return file.mkdirs();
	}

	/**
	 * 保证文件目录树存在
	 * 
	 * @param filePath
	 */
	public static void makeSureFilePath(String filePath) {
		createDir(filePath);
	}

	/**
	 * 保证文件名前缀目录树存在
	 * 
	 * @param fileName
	 */
	public static void makeSureFileName(String fileName) {
		String folderPath = splitFilePath(fileName);
		createDir(folderPath);
	}

	/**
	 * 读取文件
	 * 
	 * @param filePath
	 * @return
	 * @throws Exception
	 */
	public static String readTextFile(String filePath) throws Exception {
		File file = new File(filePath);
		InputStream inputStream = new FileInputStream(file);
		StringBuffer stringBuffer = new StringBuffer();
		int byteRead = 0;
		while ((byteRead = inputStream.read()) != -1) {
			stringBuffer.append((char) byteRead);
		}
		inputStream.close();
		return stringBuffer.toString();
	}

	/**
	 * 把文件按行读取
	 * 
	 * @param filePath
	 * @param lines
	 * @return
	 * @throws Exception
	 */
	public static boolean readTextFileLines(String filePath, List<String> lines) throws Exception {
		InputStreamReader inputReader = null;
		BufferedReader bufferReader = null;
		try {
			inputReader = new InputStreamReader(new FileInputStream(filePath));
			bufferReader = new BufferedReader(inputReader);

			// 读取一行
			String line = null;
			while ((line = bufferReader.readLine()) != null) {
				lines.add(line);
			}
			return true;
		} catch (Exception e) {
			throw e;
		} finally {
			if (inputReader != null) {
				inputReader.close();
			}
			if (bufferReader != null) {
				bufferReader.close();
			}
		}
	}

	/**
	 * json字符串转换为map对象
	 * 
	 * @param jsonString
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Map jsonToMap(String json) {
		JSONObject jsonObject = JSONObject.fromObject(json);
		if (jsonObject == null) {
			return null;
		}
		Map valueMap = new HashMap();
		Iterator it = jsonObject.keys();
		while (it.hasNext()) {
			String key = (String) it.next();
			valueMap.put(key, jsonObject.get(key));
		}
		return valueMap;
	}

	/**
	 * map对象转换为json对象
	 * 
	 * @param jsonMap
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static String mapToString(Map map) {
		JSONObject jsonString = JSONObject.fromObject(map);
		return jsonString.toString();
	}

	/**
	 * 转换ip到整数
	 * 
	 * @param ip
	 * @return
	 */
	public static int convertIp2Int(String ip) {
		String[] ips = ip.split("\\.");
		return (Integer.parseInt(ips[0]) << 24) + (Integer.parseInt(ips[1]) << 16) + (Integer.parseInt(ips[2]) << 8)
				+ Integer.parseInt(ips[3]);
	}

	/**
	 * 获取域
	 * 
	 * @param instance
	 * @param attrName
	 * @return
	 */
	public static Field getClassField(Object instance, String attrName) {
		Field field = null;
		try {
			try {
				Class<?> instanceClass = instance.getClass();
				do {
					try {
						field = instanceClass.getField(attrName);
					} catch (Exception e) {
						instanceClass = instanceClass.getSuperclass();
					}
				} while (field == null);
			} catch (Exception e) {
				Class<?> instanceClass = instance.getClass();
				do {
					try {
						field = instanceClass.getDeclaredField(attrName);
					} catch (Exception ex) {
						instanceClass = instanceClass.getSuperclass();
					}
				} while (field == null);
			}
		} catch (Exception e) {
		}

		if (field != null) {
			field.setAccessible(true);
		}
		return field;
	}

	/**
	 * 对象转换为字节数组
	 * 
	 * @param object
	 * @return
	 */
	public static byte[] objectToBytes(Object object) {
		byte[] bytes = null;
		try {
			ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
			ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
			objectOutputStream.writeObject(object);
			bytes = byteArrayOutputStream.toByteArray();
			byteArrayOutputStream.close();
			objectOutputStream.close();
		} catch (Exception e) {
			GameMonitor.catchException(e);
		}
		return bytes;
	}

	/**
	 * 字节数组转换为实体对象
	 * 
	 * @param battleBytes
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T bytesToObject(byte[] bytes, int offset, int size) {
		if (bytes != null) {
			try {
				ByteArrayInputStream bais = new ByteArrayInputStream(bytes, offset, size);
				ObjectInputStream ois = new ObjectInputStream(bais);
				return (T) ois.readObject();
			} catch (Exception e) {
				GameMonitor.catchException(e);
			}
		}
		return null;
	}

	/**
	 * 解析http请求参数格式
	 * 
	 * @param queryParam
	 * @return
	 */
	public static Map<String, String> parseHttpParam(String queryParam) {
		Map<String, String> paramMap = new HashMap<String, String>();
		try {
			if (queryParam != null && queryParam.length() > 0) {
				queryParam = URLDecoder.decode(queryParam, "UTF-8");
				String[] querys = queryParam.split("&");
				for (String query : querys) {
					String[] pair = query.split("=");
					if (pair.length == 2) {
						paramMap.put(pair[0], pair[1]);
					}
				}
			}
		} catch (Exception e) {
			GameMonitor.catchException(e);
		}
		return paramMap;
	}

	/**
	 * 检测字符串的编辑距离, 相似度判断
	 * 
	 * @param str1
	 * @param str2
	 * @return
	 */
	public static float levenshtein(String str1, String str2) {
		// 计算两个字符串的长度。
		int len1 = str1.length();
		int len2 = str2.length();
		int[][] dif = new int[len1 + 1][len2 + 1];
		for (int a = 0; a <= len1; a++) {
			dif[a][0] = a;
		}
		for (int a = 0; a <= len2; a++) {
			dif[0][a] = a;
		}

		int temp = 0;
		for (int i = 1; i <= len1; i++) {
			for (int j = 1; j <= len2; j++) {
				if (str1.charAt(i - 1) == str2.charAt(j - 1)) {
					temp = 0;
				} else {
					temp = 1;
				}
				dif[i][j] = Math.min(Math.min(dif[i - 1][j - 1] + temp, dif[i][j - 1] + 1), dif[i - 1][j] + 1);
			}
		}

		// 计算相似度similarity
		// (dif[len1][len2] 为差异步骤)
		return 1 - (float) dif[len1][len2] / Math.max(str1.length(), str2.length());
	}

	/**
	 * 解析http请求的参数
	 * 
	 * @param uriQuery
	 * @return
	 */
	public static Map<String, String> parseHttpParam(HttpExchange httpExchange) {
		Map<String, String> paramMap = new HashMap<String, String>();
		try {
			String uriPath = httpExchange.getRequestURI().getPath();
			String uriQuery = httpExchange.getRequestURI().getQuery();
			if (uriQuery != null && uriQuery.length() > 0) {
				uriQuery = URLDecoder.decode(uriQuery, "UTF-8");
				GameLog.logPrintln("UriQuery: " + uriPath + "?" + uriQuery);
				if (uriQuery != null) {
					String[] querys = uriQuery.split("&");
					for (String query : querys) {
						String[] pair = query.split("=");
						if (pair.length == 2) {
							paramMap.put(pair[0], pair[1]);
						}
					}
				}
			}
		} catch (Exception e) {
			GameMonitor.catchException(e);
		}
		return paramMap;
	}

	/**
	 * 统一的回应消息发送接口
	 * 
	 * @param httpExchange
	 * @param response
	 */
	public static void sendHttpResponse(HttpExchange httpExchange, String response) {
		if (httpExchange != null) {
			try {
				byte[] bytes = response.getBytes("UTF-8");
				httpExchange.sendResponseHeaders(200, bytes.length);
				OutputStream os = httpExchange.getResponseBody();
				os.write(bytes);
				os.close();
			} catch (Exception e) {
				GameMonitor.catchException(e);
			}
		}
	}
}
