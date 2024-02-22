package com.daiyc.criteria.core.builder.impl;

import com.daiyc.criteria.core.builder.Builder;
import com.daiyc.criteria.core.builder.CriteriaBuilder;
import com.daiyc.criteria.core.model.Combinator;
import com.daiyc.criteria.core.model.Criteria;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author daiyc
 */
@Getter
@RequiredArgsConstructor
public class DefaultCriteriaBuilder<T> implements CriteriaBuilder<T> {
    private final Combinator combinator;

    private final List<Builder<?>> builders;

    @Override
    public Criteria toCriteria() {
        List<Criteria> criteriaList = builders.stream()
                .map(Builder::toCriteria)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        if (criteriaList.isEmpty()) {
            return null;
        }

        return Optional.ofNullable(Criteria.newCriteria(combinator, criteriaList, Collections.emptyList()))
                .map(Criteria::reduce)
                .orElse(null);
    }

    @Override
    public CriteriaBuilder<T> and(List<Builder<?>> builders) {
        return createGroup(Combinator.AND, builders);
    }

    @Override
    public CriteriaBuilder<T> or(List<Builder<?>> builders) {
        return createGroup(Combinator.OR, builders);
    }

    protected CriteriaBuilder<T> createGroup(Combinator combinator, List<Builder<?>> builders) {
        if (builders == null || builders.isEmpty()) {
            return this;
        }

        DefaultCriteriaBuilder<T> newGroup = new DefaultCriteriaBuilder<>(combinator, builders);
        return new DefaultCriteriaBuilder<>(Combinator.AND, Arrays.asList(this, newGroup));
    }
}
