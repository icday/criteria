package com.daiyc.criteria.core.generic;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import lombok.SneakyThrows;

import java.io.IOException;
import java.io.InputStream;

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

    public static ConditionReader getInstance() {
        return INSTANCE;
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
