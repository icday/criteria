package com.daiyc.criteria.core.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author daiyc
 */
@Getter
@RequiredArgsConstructor
public enum Combinator {
    /**
     * AND
     */
    AND(20),

    /**
     * OR
     */
    OR(10),
    ;

    private final int priority;
}
