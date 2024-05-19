package com.daiyc.criteria.core.annotations;

import java.lang.annotation.*;

/**
 * @author daiyc
 */
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Schema {
    /**
     * Schema class
     */
    Class<?> value();
}
