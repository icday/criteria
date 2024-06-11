package com.daiyc.criteria.core.model;

import com.daiyc.criteria.core.matcher.Matcher;
import com.daiyc.criteria.core.schema.CriteriaSchema;
import com.daiyc.criteria.core.schema.SchemaFactory;
import com.daiyc.criteria.core.transform.*;

import java.util.Date;

/**
 * @author daiyc
 */
public interface Condition {
    <T> T transform(Transformer<T> transformer, TransformContext ctx);

    default <T> T transform(Transformer<T> transformer) {
        return transform(transformer, (CriteriaSchema) null);
    }

    default <T> T transform(Transformer<T> transformer, CriteriaSchema schema) {
        return transform(transformer, new TransformContext(this, schema));
    }

    default boolean match(Object value, CriteriaSchema schema) {
        return transform(new Matcher(value), schema);
    }

    default boolean match(Object value) {
        assert value != null;
        return transform(new Matcher(value), SchemaFactory.getByBean(value));
    }

    default Condition simplify() {
        return transform(new Simplify());
    }

    default Condition evaluate() {
        return evaluate(new EvaluateContext());
    }

    default Condition evaluate(Date date) {
        return evaluate(new EvaluateContext(date));
    }

    default Condition evaluate(EvaluateContext ctx) {
        return this;
    }

    default String format() {
        return transform(new Stringify());
    }
}
