package com.daiyc.criteria.core.transform;

import com.daiyc.criteria.core.model.Combinator;
import com.daiyc.criteria.core.model.Criteria;
import com.daiyc.criteria.core.model.Criterion;

import java.util.Collection;

/**
 * @author daiyc
 */
public interface Transformer<U> {
    /**
     * @param criteria 被转换的原始复合节点
     * @param newValue 执行转换后的结果
     * @param ctx      转换上下文
     * @return 对[transformed]进一步的操作结果
     */
    U transform(Criteria criteria, U newValue, TransformContext ctx);

    U transform(Criterion<?> criterion, TransformContext ctx);

    /**
     * 将转换后的子元素组装成一个新节点
     */
    U combine(Combinator combinator, Collection<U> list);
}
