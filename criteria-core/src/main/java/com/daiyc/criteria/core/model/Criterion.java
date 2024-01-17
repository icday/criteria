package com.daiyc.criteria.core.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author daiyc
 */
@Data
@AllArgsConstructor
public class Criterion<T> {
    private String fieldName;

    private Operator operator;

    private T singleValue;

    private List<T> listValue;

    public Criterion(String fieldName, Operator operator, T singleValue) {
        this(fieldName, operator, singleValue, null);
    }

    public Criterion(String fieldName, Operator operator, List<T> listValue) {
        this(fieldName, operator, null, listValue);
    }

    @Override
    public String toString() {
        OperandNum operandNum = operator.getOperandNum();
        if (operandNum == OperandNum.NONE) {
            return String.format("%s %s", fieldName, operator.getSymbol());
        } else if (operandNum == OperandNum.SINGLE) {
            return String.format("%s %s %s", fieldName, operator.getSymbol(), singleValue);
        } else if (operandNum == OperandNum.DOUBLE) {
            int size = listValue.size();
            return String.format("%s %s (%s, %s)", fieldName, operator.getSymbol()
                    , size < 1 ? null : listValue.get(0)
                    , size < 2 ? null : listValue.get(1)
            );
        } else {
            return String.format("%s %s (%s)", fieldName, operator.getSymbol()
                    , listValue.stream().map(Object::toString).collect(Collectors.joining(", "))
            );
        }
    }
}
