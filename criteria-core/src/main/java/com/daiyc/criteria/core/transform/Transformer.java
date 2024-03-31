package com.daiyc.criteria.core.transform;

import com.daiyc.criteria.core.model.Combinator;
import com.daiyc.criteria.core.model.Criteria;
import com.daiyc.criteria.core.model.Criterion;

import java.util.Collection;
import java.util.function.Supplier;

/**
 * @author daiyc
 */
public interface Transformer<U> {
    U transform(Criteria criteria, Supplier<U> transformedSupplier, TransformContext ctx);

    U transform(Criterion<?> criterion, TransformContext ctx);

    U combine(Combinator combinator, Collection<U> list);
}
