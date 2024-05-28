package com.daiyc.criteria.core.schema.impl;

import com.daiyc.criteria.core.schema.CriteriaSchema;
import com.daiyc.criteria.core.schema.SchemaParser;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

/**
 * @author daiyc
 */
public class BeanClassParser implements SchemaParser {
    @Override
    public CriteriaSchema parser(Class<?> clazz) {
//        List<FieldInfo> fieldInfos = FieldUtils.getAllFieldsList(clazz)
//                .stream()
//                .filter(this::isProperty)
//                .map(field -> {
//                    String name = field.getName();
//                    Class<?> type = field.getType();
//                    Alias[] aliases = field.getAnnotationsByType(Alias.class);
//                    Map<String, String> aliasMap = Arrays.stream(aliases)
//                            .collect(Collectors.toMap(Alias::group, Alias::name));
//                    if (!Collection.class.isAssignableFrom(type)) {
//                        return new FieldInfo(name, field, type, aliasMap);
//                    } else {
//                        Class<?> realType = (Class<?>) ((ParameterizedType) field.getGenericType()).getActualTypeArguments()[0];
//                        return new FieldInfo(name, field, realType, aliasMap);
//                    }
//                })
//                .collect(Collectors.toList());
//
//        if (fieldInfos.isEmpty()) {
//            return null;
//        }
//        return new CriteriaSchema(fieldInfos);
        return null;
    }

    private boolean isProperty(Field field) {
        int modifiers = field.getModifiers();
        return !Modifier.isStatic(modifiers);
    }
}
