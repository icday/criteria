package com.daiyc.criteria.core.facade.impl;

import com.daiyc.criteria.core.facade.ConditionMapper;
import com.daiyc.criteria.core.generic.GenericCondition;
import com.daiyc.criteria.core.generic.OperatorRegistry;
import com.daiyc.criteria.core.model.Condition;
import com.daiyc.criteria.core.model.Operator;
import com.daiyc.criteria.core.schema.CriteriaSchema;
import com.daiyc.criteria.core.schema.SchemaFactory;
import com.daiyc.criteria.core.transform.GenericTransformer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import lombok.SneakyThrows;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author daiyc
 */
public class DefaultConditionMapper implements ConditionMapper {
    private final OperatorRegistry operatorRegistry = new OperatorRegistry();

    private final static ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    private final CollectionType CONDITIONS_TYPE = OBJECT_MAPPER.getTypeFactory().constructCollectionType(List.class, GenericCondition.class);

    public DefaultConditionMapper(Operator... operators) {
        register(operators);
    }

    @Override
    public void register(List<Operator> operators) {
        operators.forEach(operatorRegistry::register);
    }

    @Override
    public Condition read(String json, Class<?> schemaClass) {
        CriteriaSchema schema = SchemaFactory.create(schemaClass);
        return read(json, schema);
    }

    @Override
    @SneakyThrows
    public Condition read(String json, CriteriaSchema schema) {
        GenericCondition genericCondition = OBJECT_MAPPER.readValue(json, GenericCondition.class);
        return genericCondition.map(schema, operatorRegistry);
    }

    @Override
    @SneakyThrows
    public List<Condition> readList(String json, Class<?> schemaClass) {
        CriteriaSchema schema = SchemaFactory.create(schemaClass);
        return readList(json, schema);
    }

    @SneakyThrows
    @Override
    public List<Condition> readList(String json, CriteriaSchema schema) {
        List<GenericCondition> conditions = OBJECT_MAPPER.readValue(json, CONDITIONS_TYPE);
        return conditions.stream()
                .map(i -> i.map(schema, operatorRegistry))
                .collect(Collectors.toList());
    }

    @Override
    public List<Condition> convert(List<GenericCondition> conditions, Class<?> schemaClass) {
        return convert(conditions, SchemaFactory.create(schemaClass));
    }

    @Override
    public Condition convert(GenericCondition condition, Class<?> schemaClass) {
        return convert(condition, SchemaFactory.create(schemaClass));
    }

    @Override
    public Condition convert(GenericCondition condition, CriteriaSchema schema) {
        return condition.map(schema, operatorRegistry);
    }

    @SneakyThrows
    @Override
    public String toJson(Condition condition) {
        GenericCondition gc = condition.transform(new GenericTransformer());
        return OBJECT_MAPPER.writeValueAsString(gc);
    }

    @Override
    public CriteriaSchema getSchema(Class<?> schemaClass) {
        return SchemaFactory.create(schemaClass);
    }

    @SneakyThrows
    @Override
    public String toSchemaJson(CriteriaSchema schema) {
        return OBJECT_MAPPER.writeValueAsString(schema);
    }

    @Override
    public String toSchemaJson(Class<?> schemaClass) {
        return toSchemaJson(SchemaFactory.create(schemaClass));
    }

    @Override
    @SneakyThrows
    public CriteriaSchema toSchema(String json) {
        return OBJECT_MAPPER.readValue(json, CriteriaSchema.class);
    }
}
