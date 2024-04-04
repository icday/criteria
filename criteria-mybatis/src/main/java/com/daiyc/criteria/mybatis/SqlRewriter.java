package com.daiyc.criteria.mybatis;

import com.daiyc.criteria.core.model.*;
import com.daiyc.criteria.core.transform.Rewriter;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author daiyc
 */
public class SqlRewriter implements Rewriter {
    @Override
    public Element rewrite(Criteria criteria) {
        return criteria;
    }

    @Override
    public Element rewrite(Criterion<?> criterion) {
        Operator operator = criterion.getOperator();
        if (Objects.requireNonNull(operator) == OperatorEnum.IN) {
            List<?> listValue = criterion.getListValue();
            List<Criterion<?>> criterionList = listValue.stream()
                    .map(v -> new Criterion<>(criterion.getFieldName(), OperatorEnum.EQ, v))
                    .collect(Collectors.toList());
            return Criteria.or(null, criterionList);
        }
        return criterion;
    }
}
