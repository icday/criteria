package com.daiyc.criteria.mybatis;

import com.daiyc.criteria.core.model.Criteria;
import com.daiyc.criteria.core.model.Criterion;
import com.daiyc.criteria.core.model.Element;
import com.daiyc.criteria.core.transform.Rewriter;
import com.daiyc.criteria.mybatis.rewriter.ContainsAnyRewriter;
import com.daiyc.criteria.mybatis.rewriter.OperatorRewriter;

import java.util.Collections;
import java.util.List;

/**
 * @author daiyc
 */
public class SqlRewriter implements Rewriter {
    private static final List<OperatorRewriter> REWRITER_LIST = Collections.singletonList(new ContainsAnyRewriter());

    @Override
    public Element rewrite(Criteria criteria) {
        return criteria;
    }

    @Override
    public Element rewrite(Criterion<?> criterion) {
        return REWRITER_LIST.stream()
                .filter(r -> r.isSupport(criterion.getOperator()))
                .findFirst()
                .map(r -> r.rewrite(criterion))
                .orElse(criterion);
    }
}
