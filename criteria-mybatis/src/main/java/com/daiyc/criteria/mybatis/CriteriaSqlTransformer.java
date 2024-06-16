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

        register(OperatorEnum.IS_NULL,
                (path, criterion, targetFieldName) -> String.format("`%s` IS NULL", targetFieldName));
        register(OperatorEnum.IS_NOT_NULL,
                (path, criterion, targetFieldName) -> String.format("`%s` IS NOT NULL", targetFieldName));

        register(OperatorEnum.EQ, new BinaryOperatorTransformer("="));
        register(OperatorEnum.NEQ, new BinaryOperatorTransformer("!="));
        register(OperatorEnum.LT, new BinaryOperatorTransformer("<"));
        register(OperatorEnum.LTE, new BinaryOperatorTransformer("<="));
        register(OperatorEnum.GT, new BinaryOperatorTransformer(">"));
        register(OperatorEnum.GTE, new BinaryOperatorTransformer(">="));
        register(OperatorEnum.LIKE, new BinaryOperatorTransformer("LIKE"));
        register(OperatorEnum.NOT_LIKE, new BinaryOperatorTransformer("NOT LIKE"));
        register(OperatorEnum.IN, new InTransformer());
        register(OperatorEnum.NOT_IN, new NotInTransformer());
        register(OperatorEnum.CONTAINS_ALL, new ContainsAllTransformer());
    }

    private static void register(Operator operator, OperatorSqlTransformer opts) {
        OPERATOR_TRANSFORMER_MAP.put(operator, opts);
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
        return operatorSqlTransformer.transform(path, criterion, ctx.getSchema());
    }

    @Override
    public String combine(Combinator combinator, List<String> list) {
        return list.stream().collect(Collectors.joining(" " + combinator.name() + " "));
    }
}
