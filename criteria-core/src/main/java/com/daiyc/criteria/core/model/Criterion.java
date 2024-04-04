package com.daiyc.criteria.core.model;

import com.daiyc.criteria.core.transform.Rewriter;
import com.daiyc.criteria.core.transform.TransformContext;
import com.daiyc.criteria.core.transform.Transformer;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author daiyc
 */
@Data
@AllArgsConstructor
public class Criterion<T> implements Element {
    private String fieldName;

    private OperatorEnum operatorEnum;

    private T singleValue;

    private List<T> listValue;

    public Criterion(String fieldName, OperatorEnum operatorEnum, T singleValue) {
        this(fieldName, operatorEnum, singleValue, null);
    }

    public Criterion(String fieldName, OperatorEnum operatorEnum, List<T> listValue) {
        this(fieldName, operatorEnum, null, listValue);
    }

    @Override
    public String toString() {
        OperandNum operandNum = operatorEnum.getOperandNum();
        if (operandNum == OperandNum.NONE) {
            return String.format("%s %s", fieldName, operatorEnum.getSymbol());
        } else if (operandNum == OperandNum.SINGLE) {
            return String.format("%s %s %s", fieldName, operatorEnum.getSymbol(), singleValue);
        } else if (operandNum == OperandNum.DOUBLE) {
            int size = listValue.size();
            return String.format("%s %s (%s, %s)", fieldName, operatorEnum.getSymbol()
                    , size < 1 ? null : listValue.get(0)
                    , size < 2 ? null : listValue.get(1)
            );
        } else {
            return String.format("%s %s (%s)", fieldName, operatorEnum.getSymbol()
                    , listValue.stream().map(Object::toString).collect(Collectors.joining(", "))
            );
        }
    }

    @Override
    public <R> R transform(Transformer<R> transformer, TransformContext ctx) {
        return transformer.transform(this, ctx);
    }

    @Override
    public Element accept(Rewriter rewriter) {
        return rewriter.rewrite(this);
    }
}
