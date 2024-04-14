package com.daiyc.criteria.core.model;

import com.daiyc.criteria.core.transform.Stringify;
import com.daiyc.criteria.core.transform.TransformContext;
import com.daiyc.criteria.core.transform.Transformer;
import lombok.Data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author daiyc
 */
@Data
public class Criteria implements Condition {
    private final Combinator combinator;

    private final List<Condition> children;

    Criteria(Combinator combinator, List<Condition> children) {
        assert combinator != Combinator.NOT || children.size() == 1;

        this.combinator = combinator;
        this.children = children;
    }

    public static Condition not(Condition condition) {
        if (condition == null) {
            return null;
        }

        return newCriteria(Combinator.NOT, Collections.singletonList(condition));
    }

    public static Condition or(List<? extends Condition> elements) {
        return newCriteria(Combinator.OR, new ArrayList<>(elements));
    }

    public static Condition and(List<? extends Condition> elements) {
        return newCriteria(Combinator.AND, new ArrayList<>(elements));
    }

    public static Condition newCriteria(final Combinator combinator, List<Condition> conditions) {
        if (conditions == null || conditions.isEmpty()) {
            return null;
        }

        if (conditions.size() == 1 && combinator != Combinator.NOT) {
            return conditions.get(0);
        }

        return new Criteria(combinator, conditions);
    }

    @Override
    public <T> T transform(Transformer<T> transformer, TransformContext ctx) {
        List<T> tList = new ArrayList<>();
        for (int i = 0; i < children.size(); i++) {
            TransformContext newCtx = ctx.next(i);
            Condition condition = children.get(i);
            T t;
            if (condition instanceof Criteria) {
                Criteria subCriteria = (Criteria) condition;
                // 递归
                T subValue = subCriteria.transform(transformer, newCtx);
                t = transformer.transform(subCriteria, subValue, newCtx);
            } else {
                Criterion<?> criterion = (Criterion<?>) condition;
                t = transformer.transform(criterion, newCtx);
            }
            tList.add(t);
        }

        return transformer.combine(combinator, tList);
    }

//    @Override
//    public Element accept(Rewriter rewriter) {
//        List<Element> elements = children.stream()
//                .map(e -> e.accept(rewriter))
//                .filter(Objects::nonNull)
//                .collect(Collectors.toList());
//
//        return new Criteria(combinator, elements);
//    }

    @Override
    public String toString() {
        return this.transform(new Stringify());
    }
}
