package com.daiyc.criteria.core.model;

import com.daiyc.criteria.core.generic.GenericCriterion;
import com.daiyc.criteria.core.schema.CriteriaSchema;

import java.util.stream.Collectors;

/**
 * 操作符元信息：
 * - 符号
 * - 参数个数，0、1、2、N
 * - 支持的参数类型
 * - 是否支持null值
 * //////////////
 * 行为：
 * - stringify
 * - isSupport
 * - toCriterion
 *
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

    <T> Criterion<T> toCriterion(GenericCriterion genericCriterion, CriteriaSchema schema);
}
