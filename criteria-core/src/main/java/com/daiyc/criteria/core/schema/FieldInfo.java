package com.daiyc.criteria.core.schema;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Map;

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
     * 实际字段
     */
    private final Field field;

    /**
     * 字段类型
     */
    private final Class<?> type;

    /**
     * 不同分组的 alias 名
     */
    private final Map<String, String> alias;

    public String getNameByGroup(String... groupNames) {
        for (String groupName : groupNames) {
            String n = alias.get(groupName);
            if (n != null) {
                return n;
            }
        }
        return name;
    }

    public <T extends Annotation> T getAnnotation(Class<T> annotationClass) {
        return field.getAnnotation(annotationClass);
    }
}
