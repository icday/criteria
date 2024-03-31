package com.daiyc.criteria.mybatis;

import com.daiyc.criteria.core.model.Criteria;
import com.daiyc.criteria.core.model.Operator;
import com.daiyc.criteria.mybatis.operator.OperatorTransformer;

import java.util.HashMap;
import java.util.Map;

/**
 * @author daiyc
 */
public class CriteriaSqlBuilder {
    private static final Map<Operator, OperatorTransformer> OPERATOR_TRANSFORMER_MAP;

    static {
        OPERATOR_TRANSFORMER_MAP = new HashMap<>();
    }

    public String provideSql(Criteria criteria) {
        return null;
    }
}
