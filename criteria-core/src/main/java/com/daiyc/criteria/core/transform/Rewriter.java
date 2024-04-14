package com.daiyc.criteria.core.transform;

import com.daiyc.criteria.core.model.Combinator;
import com.daiyc.criteria.core.model.Criteria;
import com.daiyc.criteria.core.model.Criterion;
import com.daiyc.criteria.core.model.Condition;

import java.util.List;

/**
 * 对 Tree 结构进行重写
 *
 * @author daiyc
 */
public interface Rewriter extends Transformer<Condition> {
    /**
     * 重写 criteria
     */
    Condition rewrite(Criteria criteria);

    /**
     * 重写 criterion
     */
    Condition rewrite(Criterion<?> criterion);

    @Override
    default Condition transform(Criteria criteria, Condition newValue, TransformContext ctx) {
        return rewrite(criteria);
    }

    @Override
    default Condition transform(Criterion<?> criterion, TransformContext ctx) {
        return rewrite(criterion);
    }

    @Override
    default Condition combine(Combinator combinator, List<Condition> list) {
        return Criteria.newCriteria(combinator, list);
    }
}
