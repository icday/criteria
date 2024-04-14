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
public class Criterion<T> implements Element {
    private final String fieldName;

    private final Operator operator;

    private final T singleValue;

    private final List<T> listValue;

    protected final static ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    public Criterion(String fieldName, Operator operator, T singleValue) {
        this(fieldName, operator, singleValue, null);
    }

    public Criterion(String fieldName, Operator operator, List<T> listValue) {
        this(fieldName, operator, null, listValue);
    }

    @Override
    public <R> R transform(Transformer<R> transformer, TransformContext ctx) {
        return transformer.transform(this, ctx);
    }

    @SneakyThrows
    public String getListValueJson() {
        if (listValue == null) {
            return null;
        }
        return OBJECT_MAPPER.writeValueAsString(this.listValue);
    }
}
