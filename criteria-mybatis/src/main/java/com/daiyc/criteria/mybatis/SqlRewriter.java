package com.daiyc.criteria.mybatis;

import com.daiyc.criteria.core.model.Criteria;
import com.daiyc.criteria.core.model.Criterion;
import com.daiyc.criteria.core.model.Element;
import com.daiyc.criteria.core.model.Operator;
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
        if (Objects.requireNonNull(operator) == Operator.IN) {
            List<?> listValue = criterion.getListValue();
            List<Criterion<?>> criterionList = listValue.stream()
                    .map(v -> new Criterion<>(criterion.getFieldName(), Operator.EQ, v))
                    .collect(Collectors.toList());
            return Criteria.or(null, criterionList);
        }
        return criterion;
    }
}
