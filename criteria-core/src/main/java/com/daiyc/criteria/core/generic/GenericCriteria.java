package com.daiyc.criteria.core.generic;

import com.daiyc.criteria.core.model.Combinator;
import com.daiyc.criteria.core.model.Condition;
import com.daiyc.criteria.core.model.Criteria;
import com.daiyc.criteria.core.schema.CriteriaSchema;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author daiyc
 */
@Data
public class GenericCriteria implements GenericCondition {
    private String type;

    private List<GenericCondition> elements;

    @Override
    public Condition map(CriteriaSchema schema) {
        List<Condition> conditions = this.getElements()
                .stream()
                .map(genericCondition -> genericCondition.map(schema))
                .collect(Collectors.toList());
        String type = this.getType();
        Combinator combinator = Combinator.valueOf(type.toUpperCase());
        return Criteria.newCriteria(combinator, conditions);
    }
}
