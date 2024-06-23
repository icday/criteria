package com.daiyc.criteria.core.facade;

import com.daiyc.criteria.core.facade.impl.DefaultConditionMapper;
import com.daiyc.criteria.core.generic.GenericCondition;
import com.daiyc.criteria.core.model.Condition;
import com.daiyc.criteria.core.model.Operator;
import com.daiyc.criteria.core.model.OperatorEnum;
import com.daiyc.criteria.core.schema.CriteriaSchema;
import com.daiyc.criteria.core.schema.SchemaBuilder;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author daiyc
 */
public interface ConditionMapper {
    ConditionMapper DEFAULT_MAPPER = new DefaultConditionMapper(OperatorEnum.values());

    default void register(Operator... operators) {
        register(Arrays.asList(operators));
    }

    void register(List<Operator> operators);

    Condition read(String json, Class<?> schemaClass);

    Condition read(String json, CriteriaSchema schema);

    List<Condition> readList(String json, Class<?> schemaClass);

    List<Condition> readList(String json, CriteriaSchema schema);

    List<Condition> convert(List<GenericCondition> conditions, Class<?> schemaClass);

    Condition convert(GenericCondition condition, Class<?> schemaClass);

    default List<Condition> convert(List<GenericCondition> conditions, CriteriaSchema schema) {
        return conditions.stream()
                .map(c -> convert(c, schema))
                .collect(Collectors.toList());
    }

    Condition convert(GenericCondition condition, CriteriaSchema schema);

    String toJson(Condition condition);

    CriteriaSchema getSchema(Class<?> schemaClass);

    String toSchemaJson(CriteriaSchema schema);

    String toSchemaJson(Class<?> schemaClass);

    CriteriaSchema toSchema(String json);

    default SchemaBuilder schemaBuilder() {
        return CriteriaSchema.builder();
    }
}
