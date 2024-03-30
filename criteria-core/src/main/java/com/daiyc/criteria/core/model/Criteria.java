package com.daiyc.criteria.core.model;

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

    private List<Element> children;

    public Criteria(Combinator combinator, List<Element> elements) {
        this.combinator = combinator;
        this.children = elements;
    }

    public static Criteria or(List<Criteria> criteriaList, List<Criterion<?>> criterionList) {
        return newCriteria(Combinator.OR, criteriaList, criterionList);
    }

    public static Criteria and(List<Criteria> criteriaList, List<Criterion<?>> criterionList) {
        return newCriteria(Combinator.AND, criteriaList, criterionList);
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

        if (elements.isEmpty()) {
            return null;
        }

        return new Criteria(combinator, elements);
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

    /**
     * 是否只有一个条件（非递归）
     *
     * @return is nomadic
     */

    @Override
    public <T> T transform(Transformer<T> transformer) {
        // 将 children 分成 Criteria 类型和 Criterion 类型的两个list
        List<Criteria> criteriaList = new ArrayList<>();
        List<Criterion<?>> criterionList = new ArrayList<>();
        for (Element element : children) {
            if (element instanceof Criteria) {
                Criteria subCriteria = (Criteria) element;
                criteriaList.add(subCriteria);
            } else {
                criterionList.add((Criterion<?>) element);
            }
        }

        List<T> tList = Stream.concat(
                        criteriaList.stream().map(c -> transformer.transform(c, this)),
                        criterionList.stream().map(transformer::transform)
                )
                .collect(Collectors.toList());

        return transformer.combine(combinator, tList);
    }
}
