package com.stupidbird.core.annotation.concurrency;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * The unit is not thread safe;
 * 
 * @author crazyjohn
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(value = { ElementType.TYPE, ElementType.METHOD, ElementType.FIELD })
public @interface ThreadNotSafeUnit {
	/**
	 * Just say something about this unit;
	 * 
	 * @return
	 */
	public String desc();
}
