package com.daiyc.criteria.core.builder.impl;

import com.daiyc.criteria.core.builder.MultiValueComparisonBuilder;
import com.daiyc.criteria.core.model.CriterionFactory;
import com.daiyc.criteria.core.model.OperatorEnum;

import java.util.Collections;
import java.util.List;

/**
 * @author daiyc
 */
public class MultiValueComparisonBuilderImpl<T> extends BaseComparisonBuilderImpl<T, MultiValueComparisonBuilderImpl<T>>
        implements MultiValueComparisonBuilder<T> {
    public MultiValueComparisonBuilderImpl(String name, Class<T> type) {
        super(name, type);
    }

    @Override
    public MultiValueComparisonBuilder<T> or() {
        MultiValueComparisonBuilderImpl<T> builder = new MultiValueComparisonBuilderImpl<>(name, type);
        builder.next = this;
        return builder;
    }

    @Override
    public MultiValueComparisonBuilder<T> contains(T value) {
        return addCriterion(CriterionFactory.create(name, OperatorEnum.CONTAINS_ALL, Collections.singletonList(value)));
    }

    @Override
    public MultiValueComparisonBuilder<T> containsAll(List<T> values) {
        return addCriterion((CriterionFactory.create(name, OperatorEnum.CONTAINS_ALL, values)));
    }

    @Override
    public MultiValueComparisonBuilder<T> containsAny(List<T> values) {
        return addCriterion((CriterionFactory.create(name, OperatorEnum.CONTAINS_ANY, values)));
    }
}
