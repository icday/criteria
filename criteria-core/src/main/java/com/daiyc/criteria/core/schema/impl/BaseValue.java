package com.daiyc.criteria.core.schema.impl;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author daiyc
 */
@Getter
@RequiredArgsConstructor
abstract class BaseValue<T> {
    protected final String fieldName;

    protected final Class<T> type;
}
