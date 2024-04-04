package com.daiyc.criteria.core.transform;

import com.daiyc.criteria.core.model.Criteria;
import com.daiyc.criteria.core.model.Criterion;
import com.daiyc.criteria.core.model.Element;

/**
 * @author daiyc
 */
public interface Rewriter {
    Element rewrite(Criteria criteria);

    Element rewrite(Criterion<?> criterion);
}
