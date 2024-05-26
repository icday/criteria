package com.daiyc.criteria.core.model;

import com.daiyc.criteria.core.transform.TransformContext;
import com.daiyc.criteria.core.transform.Transformer;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.SneakyThrows;

import java.util.List;

/**
 * @author daiyc
 */
@Data
@AllArgsConstructor
public class GeneralCriterion<T> implements Criterion<T> {
    private final String fieldName;

    private final Operator operator;

    private final T singleValue;

    private final List<T> listValue;

    protected final static ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    public GeneralCriterion(String fieldName, Operator operator) {
        this(fieldName, operator, null, null);
    }

    public GeneralCriterion(String fieldName, Operator operator, T singleValue) {
        this(fieldName, operator, singleValue, null);
    }

    public GeneralCriterion(String fieldName, Operator operator, List<T> listValue) {
        this(fieldName, operator, null, listValue);
    }

    @Override
    public <R> R transform(Transformer<R> transformer, TransformContext ctx) {
        return transformer.transform(this, ctx);
    }

    @Override
    @SneakyThrows
    public String getListValueJson() {
        if (listValue == null) {
            return null;
        }
        return OBJECT_MAPPER.writeValueAsString(this.listValue);
    }
}
