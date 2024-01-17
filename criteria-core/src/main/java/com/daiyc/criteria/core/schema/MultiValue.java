package com.daiyc.criteria.core.schema;

import com.daiyc.criteria.core.builder.MultiValueComparisonBuilder;
import com.daiyc.criteria.core.builder.MultiValueComparisonBuilderTrait;

/**
 * @author daiyc
 */
public interface MultiValue<T> extends Typed<T>, MultiValueComparisonBuilderTrait<T, MultiValueComparisonBuilder<T>> {
}
