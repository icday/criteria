package com.daiyc.criteria.mybatis;

import com.daiyc.criteria.core.model.Combinator;
import com.daiyc.criteria.core.model.Criteria;
import com.daiyc.criteria.core.model.Criterion;
import com.daiyc.criteria.core.model.Operator;
import com.daiyc.criteria.core.transform.TransformContext;
import com.daiyc.criteria.core.transform.Transformer;
import com.daiyc.criteria.mybatis.operator.ContainsAllTransformer;
import com.daiyc.criteria.mybatis.operator.InTransformer;
import com.daiyc.criteria.mybatis.operator.OperatorTransformer;
import com.daiyc.criteria.mybatis.operator.SimpleBinaryOperatorTransformer;
import lombok.RequiredArgsConstructor;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * @author daiyc
 */
@RequiredArgsConstructor
public class CriteriaSqlTransformer implements Transformer<String> {
    private static final Map<Operator, OperatorTransformer> OPERATOR_TRANSFORMER_MAP;

    static {
        OPERATOR_TRANSFORMER_MAP = new HashMap<>();
        OPERATOR_TRANSFORMER_MAP.put(Operator.EQ, new SimpleBinaryOperatorTransformer("="));
        OPERATOR_TRANSFORMER_MAP.put(Operator.NEQ, new SimpleBinaryOperatorTransformer("!="));
        OPERATOR_TRANSFORMER_MAP.put(Operator.LT, new SimpleBinaryOperatorTransformer("<"));
        OPERATOR_TRANSFORMER_MAP.put(Operator.LTE, new SimpleBinaryOperatorTransformer("<="));
        OPERATOR_TRANSFORMER_MAP.put(Operator.GT, new SimpleBinaryOperatorTransformer(">"));
        OPERATOR_TRANSFORMER_MAP.put(Operator.GTE, new SimpleBinaryOperatorTransformer(">="));
        OPERATOR_TRANSFORMER_MAP.put(Operator.LIKE, new SimpleBinaryOperatorTransformer("LIKE"));
        OPERATOR_TRANSFORMER_MAP.put(Operator.IN, new InTransformer());
        OPERATOR_TRANSFORMER_MAP.put(Operator.CONTAINS_ALL, new ContainsAllTransformer());
    }

    private final String rootParamName;

    @Override
    public String transform(Criteria criteria, Supplier<String> transformedSupplier, TransformContext ctx) {
        String s = transformedSupplier.get();
        Criteria parent = ctx.getParent();
        if (parent != null && parent.getCombinator().greaterThan(criteria.getCombinator())) {
            return "(" + s + ")";
        }
        return s;
    }

    @Override
    public String transform(Criterion<?> criterion, TransformContext ctx) {
        Operator operator = criterion.getOperator();
        OperatorTransformer operatorTransformer = OPERATOR_TRANSFORMER_MAP.get(operator);
        if (operatorTransformer == null) {
            throw new IllegalArgumentException("Unsupported operator: " + operator);
        }

        return operatorTransformer.transform(rootParamName + ctx.getPath(), criterion);
    }

    @Override
    public String combine(Combinator combinator, Collection<String> list) {
        return list.stream().collect(Collectors.joining(" " + combinator.name() + " "));
    }
}
