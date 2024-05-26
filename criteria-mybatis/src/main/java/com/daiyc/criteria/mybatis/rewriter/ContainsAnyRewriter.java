package com.daiyc.criteria.mybatis.rewriter;

import com.daiyc.criteria.core.model.*;

import java.util.stream.Collectors;

/**
 * @author daiyc
 */
public class ContainsAnyRewriter extends BaseOperatorRewriter {
    public ContainsAnyRewriter() {
        super(OperatorEnum.CONTAINS_ANY);
    }

    @Override
    protected Condition doRewrite(Criterion<?> criterion) {
        String fieldName = criterion.getFieldName();
        return Criteria.or(
                criterion.getListValue().stream()
                        .map(value -> CriterionFactory.create(fieldName, OperatorEnum.CONTAINS_ALL, value))
                        .collect(Collectors.toList())
        );
    }
}
