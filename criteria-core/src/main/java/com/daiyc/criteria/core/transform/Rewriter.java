package com.daiyc.criteria.core.transform;

import com.daiyc.criteria.core.model.Combinator;
import com.daiyc.criteria.core.model.Criteria;
import com.daiyc.criteria.core.model.Criterion;
import com.daiyc.criteria.core.model.Element;

import java.util.List;

/**
 * 对 Tree 结构进行重写
 *
 * @author daiyc
 */
public interface Rewriter extends Transformer<Element> {
    /**
     * 重写 criteria
     */
    Element rewrite(Criteria criteria);

    /**
     * 重写 criterion
     */
    Element rewrite(Criterion<?> criterion);

    @Override
    default Element transform(Criteria criteria, Element newValue, TransformContext ctx) {
        return rewrite(criteria);
    }

    @Override
    default Element transform(Criterion<?> criterion, TransformContext ctx) {
        return rewrite(criterion);
    }

    @Override
    default Element combine(Combinator combinator, List<Element> list) {
        return Criteria.newCriteria(combinator, list);
    }
}
