package com.daiyc.criteria.core.matcher.op;

import com.daiyc.criteria.core.model.Criterion;

/**
 * @author daiyc
 */
public interface Evaluator {
    boolean evaluate(Object value, Criterion<?> criterion);
}
