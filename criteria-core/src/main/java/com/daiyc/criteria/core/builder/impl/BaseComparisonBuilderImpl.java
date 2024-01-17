package com.daiyc.criteria.core.builder.impl;

import com.daiyc.criteria.core.builder.Builder;
import com.daiyc.criteria.core.model.Combinator;
import com.daiyc.criteria.core.model.Criteria;
import com.daiyc.criteria.core.model.Criterion;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * @author daiyc
 */
@RequiredArgsConstructor
public abstract class BaseComparisonBuilderImpl<T, B extends BaseComparisonBuilderImpl<T, B>> implements Builder<T> {
    protected final String name;

    protected final Class<T> type;

    protected List<Criterion<T>> criterionList = null;

    protected B next;

    @Override
    public Criteria toCriteria() {
        ArrayList<Criteria> orList = new ArrayList<>();

        for (BaseComparisonBuilderImpl<T, B> p = this; p != null; p = p.next) {
            if (p.isEmpty()) {
                continue;
            }

            Criteria criteria = new Criteria(Combinator.AND, null, new ArrayList<>(p.criterionList));
            orList.add(criteria);
        }

        if (orList.isEmpty()) {
            return null;
        }
        if (orList.size() == 1) {
            return orList.get(0);
        }
        return new Criteria(Combinator.OR, orList, null);
    }

    protected B addCriterion(Criterion<T> criterion) {
        if (criterionList == null) {
            criterionList = new ArrayList<>();
        }
        criterionList.add(criterion);
        return (B) this;
    }

    protected boolean isEmpty() {
        return criterionList == null || criterionList.isEmpty();
    }

    protected boolean isNotEmpty() {
        return !isEmpty();
    }
}
