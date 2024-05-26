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
     * 相对当前时间点之前
     */
    RELATIVE_BEFORE("+<", LT),

    /**
     * 相对当前时间点之前
     */
    RELATIVE_BEFORE_OR_EQUALS("+<=", LTE),

    /**
     * 相对当前时间点之后
     */
    RELATIVE_AFTER("+>", GT),

    /**
     * 相对当前时间点或之后
     */
    RELATIVE_AFTER_OR_EQUALS("+>=", GTE),

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

    private final Operator generalOperator;

    OperatorEnum(String symbol) {
        this(symbol, OperandNum.SINGLE);
    }

    OperatorEnum(String symbol, Operator generalOperator) {
        this(symbol, OperandNum.SINGLE, generalOperator);
    }

    OperatorEnum(String symbol, OperandNum operandNum) {
        this(symbol, operandNum, null);
    }

    public boolean isRelativeTimeOperator() {
        return generalOperator != null;
    }

    public static OperatorEnum symbolOf(String symbol) {
        for (OperatorEnum operator : values()) {
            if (operator.getSymbol().equalsIgnoreCase(symbol)) {
                return operator;
            }
        }

        throw new IllegalArgumentException("Invalid operator symbol: " + symbol);
    }
}
