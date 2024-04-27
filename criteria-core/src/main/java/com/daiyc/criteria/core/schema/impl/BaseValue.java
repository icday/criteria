package com.daiyc.criteria.core.schema.impl;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author daiyc
 */
@Getter
@RequiredArgsConstructor
public abstract class BaseValue<T> {
    /**
     * 字段名
     */
    protected final String fieldName;

    /**
     * 字段类型
     */
    protected final Class<T> type;
}
