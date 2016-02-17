package com.stupidbird.core.template;

import com.stupidbird.core.os.GameMonitor;

/**
 * 不可变对象修改
 * 
 * @author stupidbird
 */
public class Immutable {
	private boolean constLock = false;

	/**
	 * 默认构造
	 */
	public Immutable() {
		constLock = false;
	}

	/**
	 * 构造
	 * 
	 * @param constLock
	 */
	public Immutable(boolean constLock) {
		this.constLock = constLock;
	}

	/**
	 * 可变检测
	 * 
	 * @return
	 * @throws GameMonitor
	 */
	public boolean constCheck() {
		if (constLock) {
			throw new RuntimeException("const object rejeck modification");
		}
		return true;
	}

	/**
	 * 锁定
	 * 
	 * @param constLock
	 * @return
	 */
	public void lockConst(boolean constLock) {
		this.constLock = constLock;
	}
}
