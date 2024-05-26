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
    private final Date now;

    private final Date today;
}
