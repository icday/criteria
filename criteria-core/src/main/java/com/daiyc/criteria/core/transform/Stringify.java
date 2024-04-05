package com.daiyc.criteria.core.transform;

import com.daiyc.criteria.core.model.Combinator;
import com.daiyc.criteria.core.model.Criteria;
import com.daiyc.criteria.core.model.Criterion;

import java.util.Collection;
import java.util.stream.Collectors;

/**
 * @author daiyc
 */
public class Stringify implements Transformer<String> {
    @Override
    public String transform(Criteria criteria, String newValue, TransformContext ctx) {
        Criteria parent = ctx.getParent();

        if (parent == null) {
            return newValue;
        }

        Combinator parentCombinator = parent.getCombinator();
        Combinator combinator = criteria.getCombinator();

        if (combinator == Combinator.NOT) {
            return newValue;
        }

        if (parentCombinator.greaterThan(combinator)) {
            return "(" + newValue + ")";
        }
        return newValue;
    }

    @Override
    public String transform(Criterion<?> criterion, TransformContext ctx) {
        return criterion.getOperator().stringify(criterion);
    }

    @Override
    public String combine(Combinator combinator, Collection<String> list) {
        if (combinator == Combinator.NOT) {
            assert list.size() == 1;
            return "!(" + list.iterator().next() + ")";
        }
        return list.stream().collect(Collectors.joining(" " + combinator.name() + " "));
    }
}
