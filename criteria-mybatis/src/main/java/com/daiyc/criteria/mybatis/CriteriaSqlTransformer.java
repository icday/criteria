package com.daiyc.criteria.mybatis;

import com.daiyc.criteria.core.model.*;
import com.daiyc.criteria.core.transform.TransformContext;
import com.daiyc.criteria.core.transform.Transformer;
import com.daiyc.criteria.mybatis.operator.*;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author daiyc
 */
@RequiredArgsConstructor
public class CriteriaSqlTransformer implements Transformer<String> {
    private final String rootParamName;

    private static final Map<Operator, OperatorSqlTransformer> OPERATOR_TRANSFORMER_MAP;

    static {
        OPERATOR_TRANSFORMER_MAP = new HashMap<>();
        OPERATOR_TRANSFORMER_MAP.put(OperatorEnum.EQ, new BinaryOperatorTransformer("="));
        OPERATOR_TRANSFORMER_MAP.put(OperatorEnum.NEQ, new BinaryOperatorTransformer("!="));
        OPERATOR_TRANSFORMER_MAP.put(OperatorEnum.LT, new BinaryOperatorTransformer("<"));
        OPERATOR_TRANSFORMER_MAP.put(OperatorEnum.LTE, new BinaryOperatorTransformer("<="));
        OPERATOR_TRANSFORMER_MAP.put(OperatorEnum.GT, new BinaryOperatorTransformer(">"));
        OPERATOR_TRANSFORMER_MAP.put(OperatorEnum.GTE, new BinaryOperatorTransformer(">="));
        OPERATOR_TRANSFORMER_MAP.put(OperatorEnum.LIKE, new BinaryOperatorTransformer("LIKE"));
        OPERATOR_TRANSFORMER_MAP.put(OperatorEnum.NOT_LIKE, new BinaryOperatorTransformer("NOT LIKE"));
        OPERATOR_TRANSFORMER_MAP.put(OperatorEnum.IN, new InTransformer());
        OPERATOR_TRANSFORMER_MAP.put(OperatorEnum.NOT_IN, new NotInTransformer());
        OPERATOR_TRANSFORMER_MAP.put(OperatorEnum.CONTAINS_ALL, new ContainsAllTransformer());
    }

    @Override
    public String transform(Criteria criteria, String newValue, TransformContext ctx) {
        Criteria parent = ctx.getParent();
        if (!ctx.isRoot() && parent.getCombinator().greaterThan(criteria.getCombinator())) {
            return "(" + newValue + ")";
        }
        return newValue;
    }

    @Override
    public String transform(Criterion<?> criterion, TransformContext ctx) {
        Operator operator = criterion.getOperator();
        OperatorSqlTransformer operatorSqlTransformer = OPERATOR_TRANSFORMER_MAP.get(operator);
        if (operatorSqlTransformer == null) {
            throw new IllegalArgumentException("Unsupported operator: " + operator);
        }

        String path = ctx.getTracers().stream().map(t -> t.apply((e, i) -> String.format("children[%d]", i)))
                .collect(Collectors.joining("."));

        if (ctx.isRoot()) {
            path = rootParamName;
        } else {
            path = rootParamName + "." + path;
        }
        return operatorSqlTransformer.transform(path, criterion);
    }

    @Override
    public String combine(Combinator combinator, List<String> list) {
        return list.stream().collect(Collectors.joining(" " + combinator.name() + " "));
    }
}
