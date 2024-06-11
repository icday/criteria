package com.daiyc.criteria.core.generic;

import com.daiyc.criteria.core.model.Condition;
import com.daiyc.criteria.core.model.Operator;
import com.daiyc.criteria.core.schema.CriteriaSchema;
import com.daiyc.criteria.core.schema.FieldInfo;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;

/**
 * @author daiyc
 */
@Data
@JsonDeserialize
public class GenericCriterion implements GenericCondition {
    private String name;

    private String operator;

    private Object value;

    private String timeUnit;

    private String timePrecision;

    @Override
    public Condition map(CriteriaSchema schema, OperatorRegistry operatorRegistry) {
        Operator op = operatorRegistry.symbolOf(this.getOperator());
        FieldInfo field = schema.getField(name);
        if (field == null) {
            throw new IllegalArgumentException("Unknown field: "+ name);
        }

        return op.toCriterion(this, schema);
    }
}
