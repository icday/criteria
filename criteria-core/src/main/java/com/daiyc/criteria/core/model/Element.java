package com.daiyc.criteria.core.model;

import com.daiyc.criteria.core.transform.TransformContext;
import com.daiyc.criteria.core.transform.Transformer;

/**
 * @author daiyc
 */
public interface Element {
    default Element reduce() {
        return this;
    }

    <T> T transform(Transformer<T> transformer, TransformContext ctx);
    default <T> T transform(Transformer<T> transformer) {
        return transform(transformer, null);
    }
}
