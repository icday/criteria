package com.daiyc.criteria.core.transform;

import com.daiyc.criteria.core.model.Combinator;
import com.daiyc.criteria.core.model.Criteria;
import com.daiyc.criteria.core.model.Criterion;

import java.util.Collection;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * @author daiyc
 */
public class Stringify implements Transformer<String> {
    @Override
    public String transform(Criteria criteria, Supplier<String> transformedSupplier, TransformContext ctx) {
        String s = transformedSupplier.get();
        Criteria parent = ctx.getParent();

        if (parent != null && parent.getCombinator().greaterThan(criteria.getCombinator())) {
            return "(" + s + ")";
        }
        return s;
    }

    @Override
    public String transform(Criterion<?> criterion, TransformContext ctx) {
        return criterion.getOperator().stringify(criterion);
    }

    @Override
    public String combine(Combinator combinator, Collection<String> list) {
        return list.stream().collect(Collectors.joining(" " + combinator.name() + " "));
    }
}
