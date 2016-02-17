package com.stupidbird.core.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 日志类
 * 
 * @author stupidbird
 * 
 */
public class GameLog {
	/**
	 * 控制台打印
	 */
	static boolean consolePrint = false;
	/**
	 * 配置日志对象
	 */
	static Logger logger = LoggerFactory.getLogger("Server");
	/**
	 * 调试日志对象
	 */
	static Logger debugLogger = LoggerFactory.getLogger("Debug");
	/**
	 * 配置日志对象
	 */
	static Logger exceptionLogger = LoggerFactory.getLogger("Error");

	/**
	 * 开启控制台打印
	 * 
	 * @param enable
	 */
	public static void enableConsole(boolean enable) {
		consolePrint = enable;
	}

	/**
	 * 调试模式输出
	 * 
	 * @param msg
	 */
	public static void debugPrintln(String msg) {
		// if (Server.getInstance().isDebug()) {
		debugLogger.info(msg);

		// 控制台输出
		if (consolePrint) {
			System.out.println(msg);
		}
		// }
	}

	/**
	 * 日志打印
	 * 
	 * @param msg
	 */
	public static void logPrintln(String msg) {
		logger.info(msg);

		// 控制台输出
		if (consolePrint) {
			System.out.println(msg);
		}
	}

	/**
	 * 错误打印
	 * 
	 * @param msg
	 */
	public static void errPrintln(String msg) {
		logger.error(msg);

		// 打印错误
		System.err.println(msg);
	}

	/**
	 * 异常打印
	 * 
	 * @param excep
	 */
	public static void exceptionPrint(Exception e) {
		if (e != null) {
			// 异常信息按照错误打印
			exceptionLogger.error("Caught exception", e);
		}
	}
}
