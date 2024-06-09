package com.daiyc.criteria.core.schema;

import lombok.RequiredArgsConstructor;

import java.util.*;

/**
 * @author daiyc
 */
@RequiredArgsConstructor
public class Attributes {
    private static final String DEFAULT_GROUP = "";

    private final Map<String, Map<String, String>> data;

    private final Attributes parent;

    private String getAttribute(String attribute) {
        return getAttribute(attribute, DEFAULT_GROUP);
    }

    public String getAttribute(String attribute, String... groupNames) {
        List<String> groupList = Arrays.asList(groupNames);

        String v = groupList.stream()
                .map(it -> getAttributeOrDefault(attribute, it, null))
                .filter(Objects::nonNull)
                .findFirst()
                .orElse(null);

        if (v != null) {
            return v;
        }

        return getAttributeOrDefault(attribute, DEFAULT_GROUP, null);
    }

    public String getAttributeOrDefault(String attribute, String groupName, String defaultValue) {
        return Optional.ofNullable(data)
                .map(it -> it.get(attribute))
                .map(it -> it.get(groupName))
                .orElseGet(() -> {
                    if (parent == null) {
                        return defaultValue;
                    }
                    return parent.getAttributeOrDefault(attribute, groupName, defaultValue);
                });
    }
}
