package com.stupidbird.core.annotation.concurrency;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This unit is thread safe;
 * 
 * @author crazyjohn
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(value = { ElementType.TYPE, ElementType.METHOD, ElementType.FIELD })
public @interface ThreadSafeUnit {

}
