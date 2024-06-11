package com.daiyc.criteria.core.generic;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;

/**
 * @author daiyc
 */
public class ConditionDeserializer extends JsonDeserializer<GenericCondition> {
    @Override
    public GenericCondition deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JacksonException {
        ObjectCodec codec = p.getCodec();
        JsonNode node = codec.readTree(p);
        if (node.has("name")) {
            return codec.treeToValue(node, GenericCriterion.class);
        } else {
            return codec.treeToValue(node, GenericCriteria.class);
        }
    }
}
