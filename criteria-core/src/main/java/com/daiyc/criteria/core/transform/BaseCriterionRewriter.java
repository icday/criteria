package com.daiyc.criteria.core.transform;

import com.daiyc.criteria.core.model.Criteria;
import com.daiyc.criteria.core.model.Criterion;
import com.daiyc.criteria.core.model.Element;
import com.daiyc.criteria.core.model.Operator;

import java.util.Collection;
import java.util.Collections;

/**
 * @author daiyc
 */
public abstract class BaseCriterionRewriter implements Rewriter {
    @Override
    public final Element rewrite(Criteria criteria) {
        return criteria;
    }

    @Override
    public Element rewrite(Criterion<?> criterion) {
        if (!isSupportedOperator(criterion.getOperator())) {
            return criterion;
        }

        return doRewrite(criterion);
    }

    /**
     * 是否支持该操作符
     *
     * @param operator 操作符
     */
    protected boolean isSupportedOperator(Operator operator) {
        return supportedOperator().contains(operator);
    }

    protected Collection<Operator> supportedOperator() {
        return Collections.emptyList();
    }

    /**
     * 重写Criterion
     */
    protected abstract Element doRewrite(Criterion<?> criterion);
}
