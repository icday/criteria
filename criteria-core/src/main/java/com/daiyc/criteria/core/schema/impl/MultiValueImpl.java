package com.daiyc.criteria.core.schema.impl;

import com.daiyc.criteria.core.builder.MultiValueComparisonBuilder;
import com.daiyc.criteria.core.builder.impl.MultiValueComparisonBuilderImpl;
import com.daiyc.criteria.core.schema.MultiValue;

import java.util.List;

/**
 * @author daiyc
 */
public class MultiValueImpl<T> extends BaseValue<T> implements MultiValue<T> {
    public MultiValueImpl(String fieldName, Class<T> type) {
        super(fieldName, type);
    }

    protected MultiValueComparisonBuilder<T> builder() {
        return new MultiValueComparisonBuilderImpl<>(fieldName, type);
    }

    @Override
    public MultiValueComparisonBuilder<T> contains(T value) {
        return builder().contains(value);
    }

    @Override
    public MultiValueComparisonBuilder<T> containsAll(List<T> values) {
        return builder().containsAll(values);
    }

    @Override
    public MultiValueComparisonBuilder<T> containsAny(List<T> values) {
        return builder().containsAny(values);
    }
}
