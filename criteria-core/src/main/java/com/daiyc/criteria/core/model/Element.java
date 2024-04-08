package com.daiyc.criteria.core.model;

import com.daiyc.criteria.core.transform.*;

/**
 * @author daiyc
 */
public interface Element {
    <T> T transform(Transformer<T> transformer, TransformContext ctx);

    default <T> T transform(Transformer<T> transformer) {
        return transform(transformer, null);
    }

    Element accept(Rewriter rewriter);

    default Element simplify() {
        return transform(new Simplify());
    }

    default String format() {
        return transform(new Stringify());
    }
}
