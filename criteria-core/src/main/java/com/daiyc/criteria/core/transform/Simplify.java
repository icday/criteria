package com.daiyc.criteria.core.transform;

import com.daiyc.criteria.core.model.Combinator;
import com.daiyc.criteria.core.model.Criteria;
import com.daiyc.criteria.core.model.Criterion;
import com.daiyc.criteria.core.model.Condition;
import io.vavr.collection.Stream;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.daiyc.criteria.core.model.Combinator.NOT;

/**
 * @author daiyc
 */
public class Simplify implements Transformer<Condition> {
    @Override
    public Condition transform(Criteria criteria, Condition newValue, TransformContext ctx) {
        return newValue;
    }

    @Override
    public Condition transform(Criterion<?> criterion, TransformContext ctx) {
        return criterion;
    }

    @Override
    public Condition combine(Combinator combinator, List<Condition> list) {
        if (list == null || list.isEmpty()) {
            return null;
        }

        List<Condition> conditions = list.stream().filter(Objects::nonNull).collect(Collectors.toList());

        if (combinator == NOT) {
            assert conditions.size() == 1;
            Condition condition = list.iterator().next();
            if (condition instanceof Criteria) {
                Criteria c = (Criteria) condition;
                if (c.getCombinator() == NOT) {
                    // 双重否定 = 肯定
                    return c.getChildren().get(0);
                }
            }
            return Criteria.not(condition);
        }

        List<Condition> newConditions = Stream.ofAll(conditions)
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
