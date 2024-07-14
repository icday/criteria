package com.daiyc.criteria.mybatis;

import com.daiyc.criteria.core.model.Condition;
import com.daiyc.criteria.core.schema.CriteriaSchema;

/**
 * @author daiyc
 */
public abstract class BaseSqlProvider {
    protected String tableName;

    protected CriteriaSchema schema;

    protected String buildCondition(Condition criteria) {
        if (criteria == null) {
            return null;
        }
        return criteria.transform(new CriteriaSqlTransformer("condition"), schema);
    }
}
