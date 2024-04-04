package com.daiyc.criteria.core.model;

import com.daiyc.criteria.core.transform.Rewriter;
import com.daiyc.criteria.core.transform.Stringify;
import com.daiyc.criteria.core.transform.TransformContext;
import com.daiyc.criteria.core.transform.Transformer;
import lombok.Data;

import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author daiyc
 */
@Data
public class Criteria implements Element {
    private final Combinator combinator;

    private List<Element> children;

    public Criteria(Combinator combinator, List<Element> elements) {
        this.combinator = combinator;
        this.children = elements;
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

    protected static boolean isEmpty(List<?> list) {
        return list == null || list.isEmpty();
    }

    /**
     * (AND, [(OR, A, B)], [c, d])
     *
     * @return criteria
     */
    @Override
    public Criteria reduce() {
        List<Element> elements = this.children.stream()
                .map(Element::reduce)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        if (elements.isEmpty()) {
            return null;
        }

        Map<Boolean, List<Element>> partitions = children.stream()
                .collect(Collectors.partitioningBy(this::canMergeWith));

        List<Criteria> mergableList = partitions.get(Boolean.TRUE)
                .stream().map(e -> (Criteria) e).collect(Collectors.toList());

        List<Element> otherList = partitions.get(Boolean.FALSE);

        List<Element> mergedList = Stream.concat(
                mergableList.stream().flatMap(i -> i.children.stream()),
                otherList.stream()
        ).collect(Collectors.toList());

        return new Criteria(combinator, mergedList);
    }

    protected boolean canMergeWith(Element element) {
        if (element instanceof Criteria) {
            return ((Criteria) element).combinator == combinator;
        }
        return false;
    }

    @Override
    public <T> T transform(Transformer<T> transformer, TransformContext ctx) {
        List<T> tList = new ArrayList<>();
        for (int i = 0; i < children.size(); i++) {
            Element element = children.get(i);
            TransformContext newCtx;
            if (ctx != null) {
                newCtx = ctx.turnTo(this, i);
            } else {
                newCtx = new TransformContext(this, i);
            }
            T t;
            if (element instanceof Criteria) {
                Criteria subCriteria = (Criteria) element;
                // lazy transform subCriteria
                Supplier<T> supplier = () -> subCriteria.transform(transformer, newCtx);
                t = transformer.transform(subCriteria, supplier, newCtx);
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

        return new Criteria(combinator, elements).reduce();
    }

    @Override
    public String toString() {
        return this.transform(new Stringify());
    }
}
