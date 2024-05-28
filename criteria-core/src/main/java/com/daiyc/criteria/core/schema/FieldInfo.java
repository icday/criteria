package com.daiyc.criteria.core.schema;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

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

    private final Map<String, Map<String, String>> attributesGroup;

    public String getNameByGroup(String... groupNames) {
        return Arrays.stream(groupNames)
                .map(g -> getAttribute(Attributes.NAME, g))
                .filter(Objects::nonNull)
                .findFirst()
                .orElse(name);
    }

    public String getAttribute(String attribute) {
        return getAttribute(attribute, "");
    }

    public String getAttribute(String attribute, String groupName) {
        return getAttribute(attribute, groupName, null);
    }

    public String getAttribute(String attribute, String groupName, String defaultValue) {
        return Optional.ofNullable(attributesGroup)
                .map(it -> it.get(attribute))
                .map(it -> it.get(groupName))
                .orElse(defaultValue);
    }
}
