package com.daiyc.criteria.mybatis.operator;

import com.daiyc.criteria.core.model.Criterion;

/**
 * @author daiyc
 */
public interface OperatorTransformer {
    String transform(String path, Criterion<?> criterion);
}
