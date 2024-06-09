package com.daiyc.criteria.core.schema;

import com.daiyc.criteria.core.enums.PropertyNamingStrategy;
import com.daiyc.criteria.core.enums.SchemaAttribute;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author daiyc
 */
@Getter
@RequiredArgsConstructor
public class FieldInfo {
    /**
     * 字段名
     */
    private final String name;

    /**
     * 字段类型
     */
    private final Class<?> type;

    /**
     * 属性
     */
    private final Attributes attributes;

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
