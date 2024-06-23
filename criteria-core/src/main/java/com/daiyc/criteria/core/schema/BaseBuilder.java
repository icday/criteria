package com.daiyc.criteria.core.schema;

import java.util.HashMap;
import java.util.Map;

/**
 * @author daiyc
 */
abstract class BaseBuilder<B> {
    protected Attributes attributes;

    public B attributes(Attributes attributes) {
        this.attributes = attributes;
        return (B) this;
    }

    public B attributes(Map<String, Map<String, String>> attributeData) {
        attributeData.forEach(
                (attribute, groupData) -> groupData.forEach(
                        (groupName, value) -> this.attribute(attribute, groupName, value)
                )
        );
        return (B) this;
    }

    public B attribute(String attribute, String groupName, String value) {
        if (attributes == null) {
            attributes = new Attributes(new HashMap<>());
        }
        Map<String, Map<String, String>> data = attributes.getData();
        data.computeIfAbsent(attribute, k -> new HashMap<>())
                .put(groupName, value);
        return (B) this;
    }

    public B attribute(String attribute, String value) {
        return (B) attribute(attribute, Attributes.DEFAULT_GROUP, value);
    }
}
