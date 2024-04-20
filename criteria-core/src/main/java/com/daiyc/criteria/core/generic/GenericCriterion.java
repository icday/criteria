package com.daiyc.criteria.core.generic;

import com.daiyc.criteria.core.model.*;
import lombok.Data;

import java.util.List;

/**
 * @author daiyc
 */
@Data
public class GenericCriterion implements GenericCondition {
    private String name;

    private String operator;

    private Object value;

    @Override
    public Condition map() {
        Operator op = OperatorEnum.symbolOf(this.getOperator());
        OperandNum operandNum = op.getOperandNum();
        if (operandNum == OperandNum.DOUBLE || operandNum == OperandNum.MORE) {
            assert this.getValue() instanceof List;
            return new Criterion<>(this.getName(), op, (List<Object>) this.getValue());
        }

        if (operandNum == OperandNum.SINGLE) {
            assert this.getValue() != null;
            return new Criterion<>(this.getName(), op, this.getValue());
        }

        return new Criterion<>(this.getName(), op);
    }
}
