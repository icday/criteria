package com.daiyc.criteria.core.model;

import com.daiyc.criteria.core.transform.*;
import lombok.Data;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author daiyc
 */
@Data
public class Criteria implements Element {
    private final Combinator combinator;

    private final List<Element> children;

    public Criteria(Combinator combinator, List<Element> children) {
        assert combinator != Combinator.NOT || children.size() == 1;

        this.combinator = combinator;
        this.children = children;
    }

    public static Criteria not(Element element) {
        if (element == null) {
            return null;
        }

        return newCriteria(Combinator.NOT, Collections.singletonList(element));
    }

    public static Criteria or(List<? extends Element> elements) {
        return newCriteria(Combinator.OR, new ArrayList<>(elements));
    }

    public static Criteria and(List<? extends Element> elements) {
        return newCriteria(Combinator.AND, new ArrayList<>(elements));
    }

    public static Criteria newCriteria(final Combinator combinator, List<Element> elements) {
        if (elements == null || elements.isEmpty()) {
            return null;
        }
        return new Criteria(combinator, elements);
    }

    /**
     * (AND, [A, B, (AND, c, d)], [e, f])
     * <p>
     * => (AND, [A, B], [c, d, e, f])
     *
     * @param combinator    逻辑连接
     * @param criteriaList  条件组s
     * @param criterionList 条件s
     * @return 组成新的条件组
     */
    public static Criteria newCriteria(final Combinator combinator, List<Criteria> criteriaList, List<Criterion<?>> criterionList) {
        List<Element> elements = Stream.concat(
                Optional.ofNullable(criteriaList).orElse(Collections.emptyList()).stream(),
                Optional.ofNullable(criterionList).orElse(Collections.emptyList()).stream()
        ).collect(Collectors.toList());

        return newCriteria(combinator, elements);
    }

    @Override
    public <T> T transform(Transformer<T> transformer, TransformContext ctx) {
        List<T> tList = new ArrayList<>();
        for (int i = 0; i < children.size(); i++) {
            Element element = children.get(i);
            TransformContext newCtx;
            // TODO Context 优化
            if (ctx != null) {
                newCtx = ctx.turnTo(this, i);
            } else {
                newCtx = new TransformContext(this, i);
            }
            T t;
            if (element instanceof Criteria) {
                Criteria subCriteria = (Criteria) element;
                T subValue = subCriteria.transform(transformer, newCtx);
                t = transformer.transform(subCriteria, subValue, newCtx);
            } else {
                Criterion<?> criterion = (Criterion<?>) element;
                t = transformer.transform(criterion, newCtx);
            }
            tList.add(t);
        }

        return transformer.combine(combinator, tList);
    }

    @Override
    public Element accept(Rewriter rewriter) {
        List<Element> elements = children.stream()
                .map(e -> e.accept(rewriter))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        return new Criteria(combinator, elements);
    }

    public Criteria simplify() {
        Element element = this.transform(new Simplify());
        if (element == null) {
            return null;
        }
        if (element instanceof Criteria) {
            return (Criteria) element;
        }
        return newCriteria(Combinator.AND, Collections.singletonList(element));
    }

    @Override
    public String toString() {
        return this.transform(new Stringify());
    }
}
