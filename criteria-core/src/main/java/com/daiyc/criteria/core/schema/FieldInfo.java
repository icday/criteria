package com.daiyc.criteria.core.schema;

import com.daiyc.criteria.core.enums.DataType;
import com.daiyc.criteria.core.enums.PropertyNamingStrategy;
import com.daiyc.criteria.core.enums.SchemaAttribute;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.HashMap;

/**
 * @author daiyc
 */
@Getter
@EqualsAndHashCode(of = {"name", "type", "attributes"})
public class FieldInfo {
    /**
     * 字段名
     */
    private final String name;

    /**
     * 字段类型
     */
    private final DataType type;

    /**
     * 属性
     */
    private final Attributes attributes;

    @JsonCreator
    public FieldInfo(@JsonProperty("name") String name, @JsonProperty("type") DataType type, @JsonProperty("attributes") Attributes attributes) {
        this.name = name;
        this.type = type;
        this.attributes = attributes == null ? new Attributes(new HashMap<>()) : attributes;
    }

    public FieldInfo(String name, Class<?> clazz, Attributes attributes) {
        this(name, DataType.of(clazz), attributes);
    }

    @JsonIgnore
    public Class<?> getJavaType() {
        return type.getClazz();
    }

    public String getNameByGroup(String... groupNames) {
        String name = attributes.getAttribute(SchemaAttribute.NAME, groupNames);
        if (name != null) {
            return name;
        }

        String namingStrategy = attributes.getAttribute(SchemaAttribute.NAMING_STRATEGY, groupNames);

        if (namingStrategy == null || namingStrategy.isEmpty()) {
            return this.name;
        }

        PropertyNamingStrategy propertyNamingStrategy = PropertyNamingStrategy.valueOf(namingStrategy);
        return propertyNamingStrategy.convert(this.name);
    }

    public String getAttribute(String attribute) {
        return attributes.getAttribute(attribute);
    }

    public String getAttribute(String attribute, String... groupNames) {
        return attributes.getAttribute(attribute, groupNames);
    }
}
