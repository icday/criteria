package com.daiyc.criteria.core.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author daiyc
 */
@Getter
@RequiredArgsConstructor
public enum OperatorEnum implements Operator {
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
     * NOT IN
     */
    NOT_IN("NOT IN", OperandNum.MORE),

    /**
     * LIKE
     */
    LIKE("LIKE"),

    /**
     * NOT LIKE
     */
    NOT_LIKE("NOT LIKE"),

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

    OperatorEnum(String symbol) {
        this(symbol, OperandNum.SINGLE);
    }

    public static Operator symbolOf(String symbol) {
        for (OperatorEnum operator : values()) {
            if (operator.getSymbol().equalsIgnoreCase(symbol)) {
                return operator;
            }
        }

        throw new IllegalArgumentException("Invalid operator symbol: " + symbol);
    }
}
