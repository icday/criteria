package com.daiyc.criteria.mybatis;

import com.daiyc.criteria.core.model.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author daiyc
 */
public class CriteriaSqlTransformer implements Transformer<String> {
    @Override
    public String transform(Criteria criteria, Criteria parentCriteria) {
        String s = criteria.transform(this);
        if (parentCriteria != null && parentCriteria.getCombinator().greaterThan(criteria.getCombinator())) {
            return "(" + s + ")";
        }
        return s;
    }

    @Override
    public String transform(Criterion<?> criterion) {
        Operator operator = criterion.getOperator();
        OperandNum operandNum = operator.getOperandNum();

        List<?> listValue = criterion.getListValue();
        if (operandNum == OperandNum.NONE) {
            return String.format("%s %s", criterion.getFieldName(), operator.getSymbol());
        } else if (operandNum == OperandNum.SINGLE) {
            return String.format("%s %s %s", criterion.getFieldName(), operator.getSymbol(), criterion.getSingleValue());
        } else if (operandNum == OperandNum.DOUBLE) {
            return String.format("%s %s (%s, %s)", criterion.getFieldName(), operator.getSymbol(), listValue.get(0), listValue.get(1));
        } else {
            return String.format("%s %s (%s)", criterion.getFieldName(), operator.getSymbol()
                    , listValue.stream().map(Object::toString).collect(Collectors.joining(", ")));
        }
    }

    @Override
    public String combine(Combinator combinator, List<String> list) {
        return list.stream().collect(Collectors.joining(" " + combinator.name() + " "));
    }
}
