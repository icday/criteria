package com.daiyc.criteria.core.generic;

import com.daiyc.criteria.core.model.Operator;

import java.util.HashMap;
import java.util.Map;

/**
 * @author daiyc
 */
public class OperatorRegistry {
    private final Map<String, Operator> operatorMap = new HashMap<>();

    public void register(Operator operator) {
        operatorMap.put(operator.getSymbol(), operator);
    }

    public Operator symbolOf(String symbol) {
        return operatorMap.get(symbol);
    }
}
