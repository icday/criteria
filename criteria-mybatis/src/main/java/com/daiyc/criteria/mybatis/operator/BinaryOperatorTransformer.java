package com.daiyc.criteria.mybatis.operator;

import com.daiyc.criteria.core.model.Criterion;
import lombok.RequiredArgsConstructor;

/**
 * @author daiyc
 */
@RequiredArgsConstructor
public class BinaryOperatorTransformer implements OperatorSqlTransformer {
    private final String operator;

    @Override
    public String transform(String path, Criterion<?> criterion) {
        return String.format("`%s` %s #{%s.singleValue}", criterion.getFieldName(), operator, path);
    }
}
