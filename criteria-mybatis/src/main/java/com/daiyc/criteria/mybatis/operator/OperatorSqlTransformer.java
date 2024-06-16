package com.daiyc.criteria.mybatis.operator;

import com.daiyc.criteria.core.model.Criterion;
import com.daiyc.criteria.core.schema.CriteriaSchema;
import com.daiyc.criteria.core.schema.FieldInfo;

/**
 * @author daiyc
 */
@FunctionalInterface
public interface OperatorSqlTransformer {
    default String transform(String path, Criterion<?> criterion, CriteriaSchema schema) {
        FieldInfo field = schema.getField(criterion.getFieldName());
        return transform(path, criterion, field.getNameByGroup("mybatis", "sql", "mysql"));
    }

    String transform(String path, Criterion<?> criterion, String targetFieldName);
}
