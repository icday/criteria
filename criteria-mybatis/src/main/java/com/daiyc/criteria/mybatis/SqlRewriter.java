package com.daiyc.criteria.mybatis;

import com.daiyc.criteria.core.model.*;
import com.daiyc.criteria.core.transform.BaseCriterionRewriter;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author daiyc
 */
public class SqlRewriter extends BaseCriterionRewriter {
    @Override
    protected boolean isSupportedOperator(Operator operator) {
        return false;
    }

    @Override
    protected Element doRewrite(Criterion<?> criterion) {
        return null;
    }

    @Override
    public Element rewrite(Criterion<?> criterion) {
        Operator operator = criterion.getOperator();
        if (Objects.requireNonNull(operator) == OperatorEnum.IN) {
            List<?> listValue = criterion.getListValue();
            List<Criterion<?>> criterionList = listValue.stream()
                    .map(v -> new Criterion<>(criterion.getFieldName(), OperatorEnum.EQ, v))
                    .collect(Collectors.toList());
            return Criteria.or(criterionList);
        }
        return criterion;
    }
}
