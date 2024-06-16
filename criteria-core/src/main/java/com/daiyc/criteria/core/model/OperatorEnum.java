package com.daiyc.criteria.core.model;

import com.daiyc.criteria.core.generic.GenericCriterion;
import com.daiyc.criteria.core.model.operator.ListOperandOperator;
import com.daiyc.criteria.core.model.operator.NoOperandOperator;
import com.daiyc.criteria.core.model.operator.RelativeTimeComparator;
import com.daiyc.criteria.core.model.operator.SingleOperandOperator;
import com.daiyc.criteria.core.schema.CriteriaSchema;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

/**
 * @author daiyc
 */
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
     * 为空
     */
    IS_NULL("IS NULL", NoOperandOperator.class),

    /**
     * 不为空
     */
    IS_NOT_NULL("IS NOT NULL", NoOperandOperator.class),

    /**
     * IN
     */
    IN("IN", ListOperandOperator.class),

    /**
     * NOT IN
     */
    NOT_IN("NOT IN", ListOperandOperator.class),

    /**
     * STARTS_WITH
     */
    STARTS_WITH("STARTS_WITH"),

    /**
     * ENDS_WITH
     */
    ENDS_WITH("ENDS_WITH"),

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
    RELATIVE_BEFORE(new RelativeTimeComparator("+<", LT)),

    /**
     * 相对当前时间点之前
     */
    RELATIVE_BEFORE_OR_EQUALS(new RelativeTimeComparator("+<=", LTE)),

    /**
     * 相对当前时间点之后
     */
    RELATIVE_AFTER(new RelativeTimeComparator("+>", GT)),

    /**
     * 相对当前时间点或之后
     */
    RELATIVE_AFTER_OR_EQUALS(new RelativeTimeComparator("+>=", GTE)),

    /**
     * 包含所有
     */
    CONTAINS_ALL("contains", ListOperandOperator.class),

    /**
     * 包含任一
     */
    CONTAINS_ANY("containsAny", ListOperandOperator.class),
    ;

    @Getter
    private Operator target;

    @SneakyThrows
    OperatorEnum(String symbol, Class<? extends Operator> type)  {
        this.target = type.getConstructor(String.class).newInstance(symbol);
    }

    OperatorEnum(String symbol) {
        this(symbol, SingleOperandOperator.class);
    }

    OperatorEnum(Operator target) {
        this.target = target;
    }

    @Override
    public String getSymbol() {
        return target.getSymbol();
    }

    @Override
    public OperandNum getOperandNum() {
        return target.getOperandNum();
    }

    @Override
    public String stringify(Criterion<?> criterion) {
        return target.stringify(criterion);
    }

    @Override
    public <T> Criterion<T> toCriterion(GenericCriterion genericCriterion, CriteriaSchema schema) {
        return target.toCriterion(genericCriterion, schema);
    }

    @Override
    public Operator getTarget() {
        return target.getTarget();
    }
}
