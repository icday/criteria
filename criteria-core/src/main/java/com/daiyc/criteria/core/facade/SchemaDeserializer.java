package com.daiyc.criteria.core.facade;

import com.daiyc.criteria.core.schema.Attributes;
import com.daiyc.criteria.core.schema.CriteriaSchema;
import com.daiyc.criteria.core.schema.FieldInfo;
import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;

import java.io.IOException;
import java.util.List;

/**
 * @author daiyc
 */
public class SchemaDeserializer extends JsonDeserializer<CriteriaSchema> {
    @Override
    public CriteriaSchema deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JacksonException {
        ObjectMapper objectMapper = (ObjectMapper) p.getCodec();
        TreeNode node = objectMapper.readTree(p);

        Attributes attributes = objectMapper.treeToValue(node.get("attributes"), Attributes.class);

        CollectionType fieldsType = objectMapper.getTypeFactory()
                .constructCollectionType(List.class, FieldInfo.class);
        List<FieldInfo> fields = objectMapper.treeToValue(node.get("fields"), fieldsType);

        return new CriteriaSchema(fields, attributes);
    }
}
