package com.daiyc.criteria.core.matcher;

import com.daiyc.criteria.core.matcher.op.Evaluator;
import com.daiyc.criteria.core.matcher.op.ListOperandEvaluator;
import com.daiyc.criteria.core.matcher.op.NoOperandEvaluator;
import com.daiyc.criteria.core.matcher.op.SingleOperandEvaluator;
import com.daiyc.criteria.core.model.*;
import com.daiyc.criteria.core.transform.BaseLazyTransformer;
import com.daiyc.criteria.core.transform.TransformContext;
import io.vavr.control.Try;
import lombok.RequiredArgsConstructor;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Supplier;

/**
 * @author daiyc
 */
@SuppressWarnings("unchecked")
@RequiredArgsConstructor
public class Matcher extends BaseLazyTransformer<Boolean> {
    private final Object object;

    private final static Map<Operator, Evaluator> EVALUATOR_MAP = new HashMap<>();

    static {
        register(OperatorEnum.IS_NULL, (NoOperandEvaluator) Objects::isNull);
        register(OperatorEnum.IS_NOT_NULL, (NoOperandEvaluator) Objects::nonNull);
        register(OperatorEnum.EQ, Object.class, Objects::equals);
        register(OperatorEnum.NEQ, Object.class, (a, b) -> !Objects.equals(a, b));

        register(OperatorEnum.GT, Comparable.class, (a, b) -> a.compareTo(b) > 0);
        register(OperatorEnum.LT, Comparable.class, (a, b) -> a.compareTo(b) < 0);
        register(OperatorEnum.GTE, Comparable.class, (a, b) -> a.compareTo(b) >= 0);
        register(OperatorEnum.LTE, Comparable.class, (a, b) -> a.compareTo(b) <= 0);

        register(OperatorEnum.STARTS_WITH, String.class, StringUtils::startsWith);
        register(OperatorEnum.ENDS_WITH, String.class, StringUtils::endsWith);
        register(OperatorEnum.LIKE, String.class, StringUtils::contains);
        register(OperatorEnum.NOT_LIKE, String.class, (a, b) -> !StringUtils.contains(a, b));

        register(OperatorEnum.IN, (ListOperandEvaluator) (a, list) -> list.contains(a));
        register(OperatorEnum.NOT_IN, (ListOperandEvaluator) (a, list) -> !list.contains(a));
        register(OperatorEnum.CONTAINS_ALL, (ListOperandEvaluator) Collection::containsAll);
        register(OperatorEnum.CONTAINS_ANY, (ListOperandEvaluator) (realValue, list) -> list.stream()
                .anyMatch(realValue::contains));
    }

    public static <T> void register(OperatorEnum operator, Evaluator evaluator) {
        EVALUATOR_MAP.put(operator, evaluator);
    }

    public static <T> void register(OperatorEnum operator, Class<T> type, BiFunction<T, T, Boolean> fn) {
        EVALUATOR_MAP.put(operator, (SingleOperandEvaluator) (a, b) -> fn.apply((T) a, (T) b));
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
