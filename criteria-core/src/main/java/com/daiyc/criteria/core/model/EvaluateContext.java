package com.daiyc.criteria.core.model;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.Date;

/**
 * @author daiyc
 */
@Data
@RequiredArgsConstructor
public class EvaluateContext {
    private final Date time;

    private final Object variables;

    public EvaluateContext() {
        this(new Date());
    }

    public EvaluateContext(Date time) {
        this(time, null);
    }
}
