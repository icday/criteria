package com.daiyc.criteria.core.transform;

import com.daiyc.criteria.core.model.Combinator;
import com.daiyc.criteria.core.model.Criteria;
import com.daiyc.criteria.core.model.Criterion;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author daiyc
 */
public class Stringify extends BaseTransformer<String> {
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
    protected String not(String value) {
        return "!(" + value + ")";
    }

    @Override
    protected String doCombine(Combinator combinator, List<String> list) {
        return list.stream().collect(Collectors.joining(" " + combinator.name() + " "));
    }
}
