package com.stupidbird.core.os;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * 随机数生成封装
 * 
 * @author stupidbird
 * 
 */
public class GameRand {
	/**
	 * 控制随机数自生产变量定义
	 */
	private static final int A = 48271;
	private static final int M = 2147483647;
	private static final int Q = M / A;
	private static final int R = M % A;
	private static int State = -1;

	/**
	 * 随机整数
	 * 
	 * @return
	 */
	public static int randInt() {
		if (State < 0) {
			Random random = new Random(System.currentTimeMillis());
			State = random.nextInt();
		}

		int tmpState = A * (State % Q) - R * (State / Q);
		if (tmpState >= 0) {
			State = tmpState;
		} else {
			State = tmpState + M;
		}
		return State;
	}

	/**
	 * 限制上限的随机整数
	 * 
	 * @param max
	 * @return
	 */
	public static int randInt(int max) {
		return randInt() % (max + 1);
	}

	/**
	 * 限制范围的随机整数
	 * 
	 * @param low
	 * @param high
	 * @return
	 * @throws GameMonitor
	 */
	public static int randInt(int low, int high) throws Exception {
		if (low > high) {
			throw new Exception(String.format("Random range error: %d, %d", low, high));
		}
		return randInt(high - low) + low;
	}

	/**
	 * 随机浮点数
	 * 
	 * @return
	 */
	public static float randFloat() {
		return (float) randInt() / (float) M;
	}

	/**
	 * 设置上限的随机浮点数
	 * 
	 * @param max
	 * @return
	 */
	public static float randFloat(float max) {
		return randFloat() * max;
	}

	/**
	 * 设置范围的随机浮点数
	 * 
	 * @param low
	 * @param high
	 * @return
	 * @throws GameMonitor
	 */
	public static float randFloat(float low, float high) throws Exception {
		if (low > high) {
			throw new Exception("random range error");
		}
		return randFloat(high - low) + low;
	}

	/**
	 * 随机百分比判断
	 * 
	 * @param rate
	 *            最大100
	 * @return
	 */
	public static boolean randPercentRate(int rate) {
		try {
			int randVal = randInt(1, 100);
			if (randVal <= rate) {
				return true;
			}
		} catch (Exception e) {
			GameMonitor.catchException(e);
		}
		return false;
	}

	/**
	 * 按照权重随机一个对象
	 * 
	 * @param objList
	 * @param objWeight
	 * @return
	 */
	public static <T> T randonWeightObject(List<T> objList, List<Integer> objWeight) {
		if (objList == null || objWeight == null || objList.size() != objWeight.size()) {
			throw new RuntimeException("random weight object exception");
		}

		// 累加权重
		int totalWeight = 0;
		List<Integer> fmtWeight = new ArrayList<Integer>(objWeight.size());
		for (int i = 0; i < objWeight.size(); i++) {
			totalWeight += objWeight.get(i);
			fmtWeight.add(totalWeight);
		}

		try {
			int randomWeight = GameRand.randInt(1, totalWeight);
			for (int i = 0; i < fmtWeight.size(); i++) {
				if (randomWeight <= fmtWeight.get(i)) {
					return objList.get(i);
				}
			}
		} catch (Exception e) {
			GameMonitor.catchException(e);
		}
		return null;
	}

	/**
	 * 按照权重随机n个对象
	 * 
	 * @param objList
	 * @param objWeight
	 * @return
	 */
	public static <T> List<T> randonWeightObject(List<T> objList, List<Integer> objWeight, int count) {
		if (objList == null || objWeight == null || count <= 0 || objList.size() != objWeight.size() || count > objList.size()) {
			throw new RuntimeException("random weight object exception");
		}

		List<Integer> objWeightStub = new ArrayList<Integer>(objWeight.size());
		objWeightStub.addAll(objWeight);
		List<T> selObjList = new ArrayList<T>(count);
		while (selObjList.size() < count) {
			// 累加权重
			int totalWeight = 0;
			List<Integer> fmtWeight = new ArrayList<Integer>(objWeightStub.size());
			for (int i = 0; i < objWeightStub.size(); i++) {
				totalWeight += objWeightStub.get(i);
				fmtWeight.add(totalWeight);
			}

			try {
				int randomWeight = GameRand.randInt(1, totalWeight);
				for (int i = 0; i < fmtWeight.size(); i++) {
					if (randomWeight <= fmtWeight.get(i)) {
						selObjList.add(objList.get(i));
						objWeightStub.set(i, 0);
						break;
					}
				}
			} catch (Exception e) {
				GameMonitor.catchException(e);
			}
		}
		return selObjList;
	}

	/**
	 * 队列乱序
	 * 
	 * @param objList
	 */
	public static <T> void randomOrder(List<T> objList) {
		randomOrder(objList, 1);
	}

	/**
	 * 队列乱序
	 * 
	 * @param objList
	 */
	public static <T> void randomOrder(List<T> objList, int calcTimes) {
		for (int i = 0; i < calcTimes; i++) {
			for (int j = 0; j < objList.size(); j++) {
				try {
					int exchangeIdx = GameRand.randInt(0, objList.size() - 1);
					if (exchangeIdx != j) {
						T obj = objList.get(j);
						objList.set(j, objList.get(exchangeIdx));
						objList.set(exchangeIdx, obj);
					}
				} catch (Exception e) {
					GameMonitor.catchException(e);
				}
			}
		}
	}

	/**
	 * 随机切割
	 * 
	 * @param totalCount
	 * @param part
	 * @return
	 */
	public static List<Integer> randomIncision(int totalCount, int part, int minVal) {
		List<Integer> partCount = new LinkedList<Integer>();
		try {
			for (int i = 0; i < part; i++) {
				int value = (i == part - 1) ? totalCount : randInt(minVal, totalCount - (part - i - 1) * minVal);
				partCount.add(value);
				totalCount -= value;
			}
		} catch (Exception e) {
			GameMonitor.catchException(e);
		}
		return partCount;
	}
}
