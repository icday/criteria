package com.daiyc.criteria.mybatis.rewriter;

import com.daiyc.criteria.core.model.Criterion;
import com.daiyc.criteria.core.model.Condition;
import com.daiyc.criteria.core.model.Operator;

import java.util.Objects;

/**
 * @author daiyc
 */
abstract class BaseOperatorRewriter implements OperatorRewriter {
    protected Operator operator;

    public BaseOperatorRewriter(Operator operator) {
        this.operator = operator;
    }

    @Override
    public Condition rewrite(Criterion<?> criterion) {
        if (!isSupport(criterion.getOperator())) {
            return criterion;
        }
        return doRewrite(criterion);
    }

    protected abstract Condition doRewrite(Criterion<?> criterion);

    @Override
    public boolean isSupport(Operator operator) {
        return Objects.equals(operator, this.operator);
    }
}
