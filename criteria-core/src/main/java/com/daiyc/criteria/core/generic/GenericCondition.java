package com.daiyc.criteria.core.generic;

import com.daiyc.criteria.core.model.Condition;
import com.daiyc.criteria.core.schema.CriteriaSchema;

/**
 * @author daiyc
 */
public interface GenericCondition {
    Condition map(CriteriaSchema schema);
}
