package com.daiyc.criteria.core.transform;

import com.daiyc.criteria.core.model.Combinator;
import com.daiyc.criteria.core.model.Condition;
import com.daiyc.criteria.core.model.Criteria;
import com.daiyc.criteria.core.model.Criterion;
import io.vavr.collection.Stream;

import java.util.List;
import java.util.Objects;

import static com.daiyc.criteria.core.model.Combinator.NOT;

/**
 * @author daiyc
 */
public class Simplify extends BaseTransformer<Condition> {

    @Override
    public Condition transform(Criterion<?> criterion, TransformContext ctx) {
        return criterion;
    }

    @Override
    protected Condition not(Condition value) {
        if (value instanceof Criteria) {
            Criteria c = (Criteria) value;
            if (c.getCombinator() == NOT) {
                // 双重否定 = 肯定
                return c.getChildren().get(0);
            }
        }
        return Criteria.not(value);
    }

    @Override
    protected Condition doCombine(Combinator combinator, List<Condition> list) {
        List<Condition> newConditions = Stream.ofAll(list)
                .partition(e -> e instanceof Criteria && ((Criteria) e).getCombinator() == combinator)
                .apply((s, s2) -> {
                    List<Criteria> cs = s.map(e -> (Criteria) e).toJavaList();
                    return merge(cs, s2.toJavaList());
                });

        if (newConditions.isEmpty()) {
            return null;
        }

        if (newConditions.size() == 1) {
            return newConditions.get(0);
        }

        return Criteria.newCriteria(combinator, newConditions);
    }

    protected List<Condition> merge(List<Criteria> list, List<Condition> others) {
        return Stream.ofAll(list)
                .flatMap(Criteria::getChildren)
                .appendAll(others)
                .filter(Objects::nonNull)
                .toJavaList();
    }
}
