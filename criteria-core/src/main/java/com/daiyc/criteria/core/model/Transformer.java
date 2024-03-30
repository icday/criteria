package com.daiyc.criteria.core.model;

import java.util.List;

/**
 * @author daiyc
 */
public interface Transformer<T> {
    default T transform(Criteria criteria) {
        return transform(criteria, null);
    }

    T transform(Criteria criteria, Criteria parentCriteria);

    T transform(Criterion<?> criterion);
    
    T combine(Combinator combinator, List<T> list);
}
