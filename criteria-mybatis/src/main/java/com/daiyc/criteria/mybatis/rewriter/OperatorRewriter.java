package com.daiyc.criteria.mybatis.rewriter;

import com.daiyc.criteria.core.model.Criteria;
import com.daiyc.criteria.core.model.Element;
import com.daiyc.criteria.core.model.Operator;
import com.daiyc.criteria.core.transform.Rewriter;

/**
 * 将操作符转换成 SQL 支持的操作符
 *
 * @author daiyc
 */
public interface OperatorRewriter extends Rewriter {

    boolean isSupport(Operator operator);

    @Override
    default Element rewrite(Criteria criteria) {
        return criteria;
    }
}
