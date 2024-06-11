package com.daiyc.criteria.core.generic;

import com.daiyc.criteria.core.model.Condition;
import com.daiyc.criteria.core.schema.CriteriaSchema;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

/**
 * @author daiyc
 */
@JsonDeserialize(using = ConditionDeserializer.class)
public interface GenericCondition {
    Condition map(CriteriaSchema schema, OperatorRegistry operatorRegistry);
}
