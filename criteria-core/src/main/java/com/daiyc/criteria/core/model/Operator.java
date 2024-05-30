package com.daiyc.criteria.core.model;

import java.util.stream.Collectors;

/**
 * @author daiyc
 */
public interface Operator {
    String getSymbol();

    OperandNum getOperandNum();

    default String stringify(Criterion<?> criterion) {
        if (criterion instanceof RelativeTimeCriterion) {
            return criterion.toString();
        }
        switch (getOperandNum()) {
            case NONE:
                return String.format("%s %s", criterion.getFieldName(), getSymbol());
            case SINGLE:
                return String.format("%s %s %s", criterion.getFieldName(), getSymbol(), criterion.getSingleValue());
            case DOUBLE:
                return String.format("%s %s [%s, %s]", criterion.getFieldName(), getSymbol(),
                        criterion.getListValue().get(0), criterion.getListValue().get(1));
            case MORE:
                return String.format("%s %s (%s)", criterion.getFieldName(), getSymbol(),
                        criterion.getListValue().stream().map(Object::toString).collect(Collectors.joining(", ")));
        }
        return "";
    }
}
