package com.daiyc.criteria.core.schema;

import com.daiyc.criteria.core.builder.ValueComparisonBuilder;
import com.daiyc.criteria.core.builder.ValueComparisonBuilderTrait;

/**
 * @author daiyc
 */
public interface Value<T> extends Typed<T>, ValueComparisonBuilderTrait<T, ValueComparisonBuilder<T>> {
}
