package com.daiyc.criteria.core.annotations;

import java.lang.annotation.*;

/**
 * @author daiyc
 */
@Inherited
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface SchemaClass {
    /**
     * Schema class
     */
    Class<?> value();
}
