package com.daiyc.criteria.core.model;

import java.util.List;

/**
 * @author daiyc
 */
public interface Criterion<T> extends Condition {
    String getFieldName();

    Operator getOperator();

    T getSingleValue();

    List<T> getListValue();

    String getListValueJson();
}
