package com.daiyc.criteria.core.transform;

import com.daiyc.criteria.core.model.Combinator;
import com.daiyc.criteria.core.model.Criteria;
import com.daiyc.criteria.core.model.Criterion;

import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * 将 Element 转换为其他类型的形式
 *
 * @author daiyc
 */
public interface Transformer<U> {
    /**
     * @param criteria 被转换的原始复合节点
     * @param newValue 执行转换后的结果
     * @param ctx      转换上下文
     * @return 对[transformed]进一步的操作结果
     */
    default U transform(Criteria criteria, U newValue, TransformContext ctx) {
        return newValue;
    }

    U transform(Criterion<?> criterion, TransformContext ctx);

    /**
     * 将转换后的子元素组装成一个新节点
     */
    default U combine(Combinator combinator, List<U> list) {
        throw new UnsupportedOperationException();
    }

    default U lazyCombine(Combinator combinator, List<Supplier<U>> list) {
        return combine(combinator, list.stream().map(Supplier::get).collect(Collectors.toList()));
    }
}
