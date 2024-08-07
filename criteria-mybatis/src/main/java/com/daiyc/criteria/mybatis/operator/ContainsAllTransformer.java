package com.daiyc.criteria.mybatis.operator;

import com.daiyc.criteria.core.model.Criterion;

/**
 * @author daiyc
 */
public class ContainsAllTransformer implements OperatorSqlTransformer {
    @Override
    public String transform(String path, Criterion<?> criterion, String targetFieldName) {
        return String.format("JSON_CONTAINS(`%s`, #{%s.listValueJson})", targetFieldName, path);
    }
}
