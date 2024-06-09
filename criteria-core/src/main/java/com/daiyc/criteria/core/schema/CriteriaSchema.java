package com.daiyc.criteria.core.schema;

import lombok.Data;

import java.util.List;
import java.util.Objects;

/**
 * @author daiyc
 */
@Data
public class CriteriaSchema {
    private final List<FieldInfo> fields;

    private final Attributes attributes;

    public CriteriaSchema(List<FieldInfo> fields, Attributes attributes) {
        this.fields = fields;
        this.attributes = attributes;
    }

    public FieldInfo getField(String name) {
        return fields.stream()
                .filter(field -> Objects.equals(field.getName(), name))
                .findFirst()
                .orElse(null);
    }
}
