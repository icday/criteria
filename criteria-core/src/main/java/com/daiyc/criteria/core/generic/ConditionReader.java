package com.daiyc.criteria.core.generic;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import lombok.SneakyThrows;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * @author daiyc
 */
public class ConditionReader {
    private final static ConditionReader INSTANCE = new ConditionReader();

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    static {
        SimpleModule simpleModule = new SimpleModule();
        simpleModule.addDeserializer(GenericCondition.class, new ConditionDeserializer());
        OBJECT_MAPPER.registerModule(simpleModule);
    }

    private static final TypeFactory TYPE_FACTORY = OBJECT_MAPPER.getTypeFactory();

    private static final CollectionType CONDITION_COLLECTION_TYPE = TYPE_FACTORY.constructCollectionType(List.class, GenericCondition.class);

    public static ConditionReader getInstance() {
        return INSTANCE;
    }

    @SneakyThrows
    public List<GenericCondition> readList(byte[] input) {
        return OBJECT_MAPPER.readValue(input, CONDITION_COLLECTION_TYPE);
    }

    @SneakyThrows
    public List<GenericCondition> readList(String input) {
        return OBJECT_MAPPER.readValue(input, CONDITION_COLLECTION_TYPE);
    }

    @SneakyThrows
    public List<GenericCondition> readList(InputStream input) {
        return OBJECT_MAPPER.readValue(input, CONDITION_COLLECTION_TYPE);
    }

    @SneakyThrows
    public GenericCondition read(byte[] bytes) {
        return OBJECT_MAPPER.readValue(bytes, GenericCondition.class);
    }

    @SneakyThrows
    public GenericCondition read(String str) {
        return OBJECT_MAPPER.readValue(str, GenericCondition.class);
    }

    @SneakyThrows
    public GenericCondition read(InputStream is) {
        return OBJECT_MAPPER.readValue(is, GenericCondition.class);
    }

    public static class ConditionDeserializer extends JsonDeserializer<GenericCondition> {
        @Override
        public GenericCondition deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JacksonException {
            JsonNode node = p.getCodec().readTree(p);
            if (node.has("name")) {
                return p.getCodec().treeToValue(node, GenericCriterion.class);
            } else {
                return p.getCodec().treeToValue(node, GenericCriteria.class);
            }
        }
    }
}
