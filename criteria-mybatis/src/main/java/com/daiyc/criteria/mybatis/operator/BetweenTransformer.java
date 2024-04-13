package com.daiyc.criteria.mybatis.operator;

import com.daiyc.criteria.core.model.Criterion;

/**
 * @author daiyc
 */
public class BetweenTransformer implements OperatorSqlTransformer {
    @Override
    public String transform(String path, Criterion<?> criterion) {
        return String.format("`%s` BETWEEN (#{%s.listValue[0]}, #{%s.listValue[1]})", criterion.getFieldName(), path, path);
    }
}
