package com.daiyc.criteria.core.schema;

/**
 * @author daiyc
 */
public interface Typed<T> {
    /**
     * 字段名
     *
     * @return fieldName
     */
    String getFieldName();

    /**
     * 字段类型
     *
     * @return 类型
     */
    Class<T> getType();
}
