package com.daiyc.criteria.core.schema;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Map;

/**
 * @author daiyc
 */
@Getter
@RequiredArgsConstructor
public class FieldInfo {
    private final String name;

    private final Class<?> type;

    private final Map<String, String> alias;

    public String getNameByGroup(String groupName) {
        return alias.getOrDefault(groupName, name);
    }
}
