package com.daiyc.criteria.core.schema;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * @author daiyc
 */
@Data
@EqualsAndHashCode(of = {"fields", "attributes"})
public class CriteriaSchema {
    private final List<FieldInfo> fields;

    private final Attributes attributes;

    @JsonCreator
    public CriteriaSchema(@JsonProperty("fields") List<FieldInfo> fields, @JsonProperty("attributes") Attributes attributes) {
        this.fields = fields;
        this.attributes = attributes;

        for (FieldInfo field : fields) {
            Optional.ofNullable(field.getAttributes())
                    .ifPresent(a -> a.setParent(attributes));
        }
    }

    public FieldInfo getField(String name) {
        return fields.stream()
                .filter(field -> Objects.equals(field.getName(), name))
                .findFirst()
                .orElse(null);
    }
}
