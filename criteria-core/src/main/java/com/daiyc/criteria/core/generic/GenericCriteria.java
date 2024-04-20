package com.daiyc.criteria.core.generic;

import com.daiyc.criteria.core.model.Combinator;
import com.daiyc.criteria.core.model.Condition;
import com.daiyc.criteria.core.model.Criteria;
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
    public Condition map() {
        List<Condition> conditions = this.getElements()
                .stream()
                .map(GenericCondition::map)
                .collect(Collectors.toList());
        String type = this.getType();
        Combinator combinator = Combinator.valueOf(type.toUpperCase());
        return Criteria.newCriteria(combinator, conditions);
    }
}
