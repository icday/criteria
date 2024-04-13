package com.daiyc.criteria.mybatis.operator;

import com.daiyc.criteria.core.model.Criterion;

/**
 * @author daiyc
 */
public interface OperatorSqlTransformer {
    String transform(String path, Criterion<?> criterion);
}
