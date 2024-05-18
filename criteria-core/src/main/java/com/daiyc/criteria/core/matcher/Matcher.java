package com.daiyc.criteria.core.matcher;

import com.daiyc.criteria.core.matcher.op.Evaluator;
import com.daiyc.criteria.core.matcher.op.ListOperandEvaluator;
import com.daiyc.criteria.core.matcher.op.SingleOperandEvaluator;
import com.daiyc.criteria.core.model.*;
import com.daiyc.criteria.core.transform.BaseLazyTransformer;
import com.daiyc.criteria.core.transform.TransformContext;
import io.vavr.control.Try;
import lombok.RequiredArgsConstructor;
import org.apache.commons.beanutils.PropertyUtils;

import java.util.*;
import java.util.function.Supplier;

/**
 * @author daiyc
 */
@RequiredArgsConstructor
public class Matcher extends BaseLazyTransformer<Boolean> {
    private final Object object;

    private final static Map<Operator, Evaluator> EVALUATOR_MAP = new HashMap<>();

    static {
        EVALUATOR_MAP.put(OperatorEnum.EQ, (SingleOperandEvaluator) Objects::equals);
        EVALUATOR_MAP.put(OperatorEnum.NEQ, (SingleOperandEvaluator) (a, b) -> !Objects.equals(a, b));
        // TODO assert operand and value's type
        EVALUATOR_MAP.put(OperatorEnum.GT, (SingleOperandEvaluator) (a, b) -> {
            assert a instanceof Comparable && b instanceof Comparable;
            return ((Comparable) a).compareTo(b) > 0;
        });
        EVALUATOR_MAP.put(OperatorEnum.LT, (SingleOperandEvaluator) (a, b) -> {
            assert a instanceof Comparable && b instanceof Comparable;
            return ((Comparable) a).compareTo(b) < 0;
        });
        EVALUATOR_MAP.put(OperatorEnum.GTE, (SingleOperandEvaluator) (a, b) -> {
            assert a instanceof Comparable && b instanceof Comparable;
            return ((Comparable) a).compareTo(b) >= 0;
        });
        EVALUATOR_MAP.put(OperatorEnum.LTE, (SingleOperandEvaluator) (a, b) -> {
            assert a instanceof Comparable && b instanceof Comparable;
            return ((Comparable) a).compareTo(b) <= 0;
        });
        EVALUATOR_MAP.put(OperatorEnum.LIKE, (SingleOperandEvaluator) (a, b) -> {
            assert a instanceof String && b instanceof String;
            return ((String) a).contains((String) b);
        });
        EVALUATOR_MAP.put(OperatorEnum.NOT_LIKE, (SingleOperandEvaluator) (a, b) -> {
            assert a instanceof String && b instanceof String;
            return !((String) a).contains((String) b);
        });
        EVALUATOR_MAP.put(OperatorEnum.IN, (ListOperandEvaluator) (a, list) -> list.contains(a));
        EVALUATOR_MAP.put(OperatorEnum.NOT_IN, (ListOperandEvaluator) (a, list) -> !list.contains(a));
        EVALUATOR_MAP.put(OperatorEnum.CONTAINS_ALL, (ListOperandEvaluator) Collection::containsAll);
        EVALUATOR_MAP.put(OperatorEnum.CONTAINS_ANY, (ListOperandEvaluator) (realValue, list) -> list.stream()
                .anyMatch(realValue::contains));
    }

    @Override
    public Boolean transform(Criteria criteria, Boolean newValue, TransformContext ctx) {
        return newValue;
    }

    @Override
    public Boolean transform(Criterion<?> criterion, TransformContext ctx) {
        Operator operator = criterion.getOperator();

        // real value
        Object value = Try.of(() -> PropertyUtils.getProperty(object, criterion.getFieldName()))
                .get();
        Evaluator evaluator = EVALUATOR_MAP.get(operator);

        return evaluator.evaluate(value, criterion);
    }

    @Override
    protected Boolean not(Supplier<Boolean> value) {
        return !value.get();
    }

    @Override
    protected Boolean doCombine(Combinator combinator, List<Supplier<Boolean>> list) {
        if (combinator == Combinator.AND) {
            for (Supplier<Boolean> supplier: list) {
                if (!supplier.get()) {
                    return false;
                }
            }
            return true;
        } else {
            for (Supplier<Boolean> supplier : list) {
                if (supplier.get()) {
                    return true;
                }
            }
            return false;
        }
    }
}
