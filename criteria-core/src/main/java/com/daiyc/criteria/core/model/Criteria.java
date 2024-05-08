package com.daiyc.criteria.core.model;

import com.daiyc.criteria.core.transform.Stringify;
import com.daiyc.criteria.core.transform.TransformContext;
import com.daiyc.criteria.core.transform.Transformer;
import io.vavr.collection.Stream;
import io.vavr.control.Either;
import lombok.Data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;

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
        List<Supplier<T>> list = Stream.ofAll(children)
                .map(c -> {
                    Either<Criteria, Criterion<?>> r;
                    if (c instanceof Criteria) {
                        r = Either.left((Criteria) c);
                    } else {
                        r = Either.right((Criterion<?>) c);
                    }
                    return r;
                })
                .zipWithIndex((cond, i) -> {
                    TransformContext newCtx = ctx.next(i);
                    return cond.fold(criteria -> () -> {
                        T v = criteria.transform(transformer, newCtx);
                        return transformer.transform(criteria, v, newCtx);
                    }, criterion -> (Supplier<T>) () -> transformer.transform(criterion, newCtx));
                })
                .toJavaList();

        return transformer.lazyCombine(combinator, list);
    }

    @Override
    public String toString() {
        return this.transform(new Stringify());
    }
}
