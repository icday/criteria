package com.daiyc.criteria.mybatis.annotations;

import java.lang.annotation.*;

/**
 * @author daiyc
 */
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface CriteriaMapper {
    /**
     * 表名
     */
    String tableName();

    /**
     * schema 类
     */
    Class<?> schemaClass();
}
