package com.stupidbird.core.os;

import com.stupidbird.core.log.GameLog;

/**
 * stupidbird系统异常封装
 * 
 * @author stupidbird
 * 
 */
public class GameMonitor {

	/**
	 * 异常捕获
	 * 
	 * @param e
	 */
	public static void catchException(Exception e) {
		if (e != null) {
			GameLog.exceptionPrint(e);
		}
	}

	/**
	 * 格式化异常堆栈结构
	 * 
	 * @param e
	 * @return
	 */
	public static String formatStackMsg(Exception e) {
		if (e != null) {
			StackTraceElement[] stackArray = e.getStackTrace();
			StringBuffer sb = new StringBuffer();
			sb.append(e.toString() + "\n");

			for (int i = 0; stackArray != null && i < stackArray.length; i++) {
				StackTraceElement element = stackArray[i];
				sb.append(element.toString() + "\n");
			}

			return sb.toString();
		}
		return "";
	}

	/**
	 * 格式化异常堆栈结构
	 * 
	 * @param e
	 * @return
	 */
	public static String formatStackTrace(StackTraceElement[] stackArray, int skipCount) {
		if (stackArray != null) {
			StringBuffer sb = new StringBuffer();
			for (int i = skipCount; i < stackArray.length; i++) {
				StackTraceElement element = stackArray[i];
				sb.append(element.toString() + "\n");
			}
			return sb.toString();
		}
		return "";
	}
}
