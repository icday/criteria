package com.daiyc.criteria.core.generic;

import com.daiyc.criteria.core.model.Combinator;
import com.daiyc.criteria.core.model.Condition;
import com.daiyc.criteria.core.model.Criteria;
import com.daiyc.criteria.core.schema.CriteriaSchema;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author daiyc
 */
@Data
@JsonDeserialize
public class GenericCriteria implements GenericCondition {
    private String type;

    @JsonAlias("conditions")
    private List<GenericCondition> elements;

    @Override
    public Condition map(CriteriaSchema schema, OperatorRegistry operatorRegistry) {
        List<Condition> conditions = this.getElements()
                .stream()
                .map(genericCondition -> genericCondition.map(schema, operatorRegistry))
                .collect(Collectors.toList());
        String type = this.getType();
        Combinator combinator = Combinator.valueOf(type.toUpperCase());
        return Criteria.newCriteria(combinator, conditions);
    }
}
