package com.daiyc.criteria.core.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * @author daiyc
 */
@Data
public class Criteria {
    private final Combinator combinator;

    private final List<Criteria> criteriaList;

    private final List<Criterion<?>> criterionList;

    public Criteria(Combinator combinator, List<Criteria> criteriaList, List<Criterion<?>> criterionList) {
        this.combinator = combinator;
        this.criteriaList = criteriaList == null ? Collections.emptyList() : criteriaList;
        this.criterionList = criterionList == null ? Collections.emptyList() : criterionList;
    }

    /**
     * (AND, [A, B, (AND, c, d)], [e, f])
     * <p>
     * => (AND, [A, B], [c, d, e, f])
     *
     * @param combinator
     * @param criteriaList
     * @param criterionList
     * @return
     */
    public static Criteria newCriteria(final Combinator combinator, List<Criteria> criteriaList, List<Criterion<?>> criterionList) {
        if ((criteriaList == null || criteriaList.isEmpty()) && (criterionList == null || criterionList.isEmpty())) {
            return null;
        }

        return new Criteria(combinator, criteriaList, criterionList);
    }

    /**
     * (AND, [(OR, A, B)], [c, d])
     *
     * @return criteria
     */
    public Criteria reduce() {
        List<Criteria> criteriaList = this.getCriteriaList()
                .stream()
                .map(Criteria::reduce)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        Predicate<Criteria> isSameCombinator = c -> c.getCombinator() == combinator;

        List<Criterion<?>> newCriterionList = new ArrayList<>(criterionList);
        List<Criteria> newCriteriaList = new ArrayList<>();

        for (Criteria subCriteria : criteriaList) {
            // 具有相同的连接符
            if (isSameCombinator.test(subCriteria)) {
                newCriterionList.addAll(subCriteria.getCriterionList());
                newCriteriaList.addAll(subCriteria.getCriteriaList());
            } else if (subCriteria.isNomadic()) {
                newCriterionList.add(subCriteria.getCriterionList().get(0));
            } else if (!subCriteria.isEmpty()) {
                newCriteriaList.add(subCriteria);
            }
        }

        return newCriteria(combinator, newCriteriaList, newCriterionList);
    }

    /**
     * 是否只有一个条件（非递归）
     *
     * @return is nomadic
     */
    protected boolean isNomadic() {
        return (criteriaList == null || criterionList.isEmpty()) && (criterionList != null && criterionList.size() == 1);
    }

    public boolean isEmpty() {
        return criteriaList.isEmpty() && criterionList.isEmpty();
    }

    @Override
    public String toString() {
        String combinatorStr = " " + combinator.name() + " ";

        List<String> strList = new ArrayList<>();

        if (criterionList != null && !criterionList.isEmpty()) {
            String str = criterionList.stream()
                    .map(Criterion::toString)
                    .collect(Collectors.joining(combinatorStr));
            strList.add(str);
        }

        if (criteriaList != null && !criteriaList.isEmpty()) {
            String str = criteriaList.stream()
                    .map(c -> {
                        String s = c.toString();
//                        if (combinator.getPriority() > c.getCombinator().getPriority()) {
                            return "(" + s + ")";
//                        }
//                        return s;
                    })
                    .collect(Collectors.joining(combinatorStr));
            strList.add(str);
        }
        return String.join(combinatorStr, strList);
    }
}
