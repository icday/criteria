package com.daiyc.criteria.api;

/**
 * @author daiyc
 */
public enum Connector {
    /**
     * AND
     */
    AND,

    /**
     * OR
     */
    OR,

    /**
     * NOT，negative
     * 如果NOT连接了多个逻辑语句，它等价于NOT(AND(Criterion, ...))
     */
    NOT
}
