package com.daiyc.criteria.mybatis.annotations;

import java.lang.annotation.*;

/**
 * @author daiyc
 */
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Table {
    /**
     * 表名
     */
    String value();
}
