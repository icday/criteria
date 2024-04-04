package com.daiyc.criteria.mybatis;

import com.daiyc.criteria.core.model.Combinator;
import com.daiyc.criteria.core.model.Criteria;
import com.daiyc.criteria.core.model.Criterion;
import com.daiyc.criteria.core.model.OperatorEnum;
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
    private static final Map<OperatorEnum, OperatorTransformer> OPERATOR_TRANSFORMER_MAP;

    static {
        OPERATOR_TRANSFORMER_MAP = new HashMap<>();
        OPERATOR_TRANSFORMER_MAP.put(OperatorEnum.EQ, new SimpleBinaryOperatorTransformer("="));
        OPERATOR_TRANSFORMER_MAP.put(OperatorEnum.NEQ, new SimpleBinaryOperatorTransformer("!="));
        OPERATOR_TRANSFORMER_MAP.put(OperatorEnum.LT, new SimpleBinaryOperatorTransformer("<"));
        OPERATOR_TRANSFORMER_MAP.put(OperatorEnum.LTE, new SimpleBinaryOperatorTransformer("<="));
        OPERATOR_TRANSFORMER_MAP.put(OperatorEnum.GT, new SimpleBinaryOperatorTransformer(">"));
        OPERATOR_TRANSFORMER_MAP.put(OperatorEnum.GTE, new SimpleBinaryOperatorTransformer(">="));
        OPERATOR_TRANSFORMER_MAP.put(OperatorEnum.LIKE, new SimpleBinaryOperatorTransformer("LIKE"));
        OPERATOR_TRANSFORMER_MAP.put(OperatorEnum.IN, new InTransformer());
        OPERATOR_TRANSFORMER_MAP.put(OperatorEnum.CONTAINS_ALL, new ContainsAllTransformer());
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
        OperatorEnum operatorEnum = criterion.getOperatorEnum();
        OperatorTransformer operatorTransformer = OPERATOR_TRANSFORMER_MAP.get(operatorEnum);
        if (operatorTransformer == null) {
            throw new IllegalArgumentException("Unsupported operator: " + operatorEnum);
        }

        return operatorTransformer.transform(rootParamName + ctx.getPath(), criterion);
    }

    @Override
    public String combine(Combinator combinator, Collection<String> list) {
        return list.stream().collect(Collectors.joining(" " + combinator.name() + " "));
    }
}
