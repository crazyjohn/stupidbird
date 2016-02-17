package com.stupidbird.core.template;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 1. 配置文件基类(所有属性必须是final, 而且final属性的初始化必须在默认构造函数进行)
 * 
 * 2. 每个config必须实现protected void clearStaticData() {} 方法
 * 	          用途: 用来清理这个config对应storage的静态数据
 *    	因为经常会在config里面进行static数据定义
 *    	在热更新的时候进行数据组装, 会造成数据多份存在
 *    	这时候就需要先清理静态数据重新装载
 * 
 * @author stupidbird
 */
public abstract class BaseTemplate extends Immutable {
	/**
	 * 所属的configStorage
	 */
	private TempalteStorage storage;
	/**
	 * Id关键字注解
	 * 
	 * @author stupidbird
	 */
	@Documented
	@Retention(RetentionPolicy.RUNTIME)
	@Target({ java.lang.annotation.ElementType.FIELD, java.lang.annotation.ElementType.METHOD })
	public @interface Id {
	}

	/**
	 * 配置加载完成调用, 便于上层进行数据按照应用层要求重新构建 
	 * 备注: 返回true表示格式正确, 否则格式错误不添加进入
	 */
	protected boolean assemble() {
		return true;
	}
	
	/**
	 * 检测有消息
	 * @return
	 */
	protected boolean checkValid() {
		return true;
	}

	/**
	 * 获取所属storage
	 * 
	 * @return
	 */
	public TempalteStorage getStorage() {
		return storage;
	}

	/**
	 * 设置所属的storage
	 * 
	 * @param storage
	 */
	public void setStorage(TempalteStorage storage) {
		this.storage = storage;
	}
}
