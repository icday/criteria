package com.daiyc.criteria.core.model;

import com.daiyc.criteria.core.transform.TransformContext;
import com.daiyc.criteria.core.transform.Transformer;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * @author daiyc
 */
@Data
@AllArgsConstructor
public class Criterion<T> implements Element {
    private String fieldName;

    private Operator operator;

    private T singleValue;

    private List<T> listValue;

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

//    @Override
//    public Element accept(Rewriter rewriter) {
//        return rewriter.rewrite(this);
//    }
}
