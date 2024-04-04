package com.daiyc.criteria.core.transform;

import com.daiyc.criteria.core.model.Criteria;
import com.daiyc.criteria.core.model.Criterion;
import com.daiyc.criteria.core.model.Element;

/**
 * @author daiyc
 */
public interface Rewriter {
    /**
     * 重写 criteria
     */
    Element rewrite(Criteria criteria);

    /**
     * 重写 criterion
     */
    Element rewrite(Criterion<?> criterion);
}
