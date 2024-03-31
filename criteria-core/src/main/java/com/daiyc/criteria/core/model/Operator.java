package com.daiyc.criteria.core.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author daiyc
 */
@Getter
@RequiredArgsConstructor
public enum Operator {
    /**
     * 等于
     */
    EQ("="),

    /**
     * 不等于
     */
    NEQ("!="),

    /**
     * 小于
     */
    LT("<"),

    /**
     * 大于
     */
    GT(">"),

    /**
     * 小于等于
     */
    LTE("<="),

    /**
     * 大于等于
     */
    GTE(">="),

    /**
     * IN
     */
    IN("IN", OperandNum.MORE),

    /**
     * LIKE
     */
    LIKE("LIKE"),

    /**
     * 包含所有
     */
    CONTAINS_ALL("contains", OperandNum.MORE),

    /**
     * 包含任一
     */
    CONTAINS_ANY("containsAny", OperandNum.MORE),
    ;

    /**
     * symbol
     */
    private final String symbol;

    /**
     * 参数个数
     */
    private final OperandNum operandNum;

    Operator(String symbol) {
        this(symbol, OperandNum.SINGLE);
    }
}
