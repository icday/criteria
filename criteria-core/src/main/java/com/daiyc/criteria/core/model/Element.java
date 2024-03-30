package com.daiyc.criteria.core.model;

/**
 * @author daiyc
 */
public interface Element {
    default Element reduce() {
        return this;
    }

    <T> T transform(Transformer<T> transformer);
}
