package com.daiyc.criteria.mybatis.rewriter;

import com.daiyc.criteria.core.model.Criteria;
import com.daiyc.criteria.core.model.Criterion;
import com.daiyc.criteria.core.model.Element;
import com.daiyc.criteria.core.model.OperatorEnum;

import java.util.stream.Collectors;

/**
 * @author daiyc
 */
public class ContainsAnyRewriter extends BaseOperatorRewriter {
    public ContainsAnyRewriter() {
        super(OperatorEnum.CONTAINS_ANY);
    }

    @Override
    protected Element doRewrite(Criterion<?> criterion) {
        String fieldName = criterion.getFieldName();
        return Criteria.or(
                criterion.getListValue().stream()
                        .map(value -> new Criterion<>(fieldName, OperatorEnum.CONTAINS_ALL, value))
                        .collect(Collectors.toList())
        );
    }
}
