package com.daiyc.criteria.core.model;

import com.daiyc.criteria.core.schema.CriteriaSchema;
import com.daiyc.criteria.core.transform.*;

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

    default Condition simplify() {
        return transform(new Simplify());
    }

    default String format() {
        return transform(new Stringify());
    }
}
