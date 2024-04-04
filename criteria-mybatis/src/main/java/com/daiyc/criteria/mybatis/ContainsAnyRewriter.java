package com.daiyc.criteria.mybatis;

import com.daiyc.criteria.core.model.*;
import com.daiyc.criteria.core.transform.BaseCriterionRewriter;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author daiyc
 */
public class ContainsAnyRewriter extends BaseCriterionRewriter {

    @Override
    protected Collection<Operator> supportedOperator() {
        return Collections.singletonList(OperatorEnum.CONTAINS_ANY);
    }

    @Override
    protected Element doRewrite(Criterion<?> criterion) {
        List<?> listValue = criterion.getListValue();
        List<Criterion<?>> list = listValue.stream()
                .map(v -> new Criterion<>(criterion.getFieldName(), OperatorEnum.CONTAINS_ALL, v))
                .collect(Collectors.toList());
        return Criteria.or(
                list
        );
    }
}
