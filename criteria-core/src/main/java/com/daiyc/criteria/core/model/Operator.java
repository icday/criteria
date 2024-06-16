package com.daiyc.criteria.core.model;

import com.daiyc.criteria.core.generic.GenericCriterion;
import com.daiyc.criteria.core.schema.CriteriaSchema;

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

    String stringify(Criterion<?> criterion);

    <T> Criterion<T> toCriterion(GenericCriterion genericCriterion, CriteriaSchema schema);

    default Operator getTarget() {
        return this;
    }
}
