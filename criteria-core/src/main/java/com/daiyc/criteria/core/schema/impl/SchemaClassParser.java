package com.daiyc.criteria.core.schema.impl;

import com.daiyc.criteria.core.annotations.Attribute;
import com.daiyc.criteria.core.schema.*;
import com.google.common.base.CaseFormat;
import com.google.common.base.Converter;
import io.vavr.CheckedFunction4;
import io.vavr.Tuple;
import io.vavr.collection.Stream;
import org.apache.commons.lang3.reflect.FieldUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author daiyc
 */
public class SchemaClassParser implements SchemaParser {
    private static final Converter<String, String> NAME_CONVERTER = CaseFormat.UPPER_UNDERSCORE.converterTo(CaseFormat.LOWER_CAMEL);

    @Override
    public CriteriaSchema parser(Class<?> clazz) {
        List<FieldInfo> fieldInfos = parseFields(clazz);
        if (fieldInfos.isEmpty()) {
            return null;
        }
        return new CriteriaSchema(fieldInfos);
    }

    private static <T> List<T> mapFields(Class<?> clazz, CheckedFunction4<String, Field, Class<?>, Class<?>, T> mapper) {
        return FieldUtils.getAllFieldsList(clazz).stream()
                .filter(SchemaClassParser::isStaticTyped)
                .map(field -> {
                    String name = NAME_CONVERTER.convert(field.getName());
                    Class<?> type = field.getType();
                    Class<?> realType = (Class<?>) ((ParameterizedType) field.getGenericType()).getActualTypeArguments()[0];
                    try {
                        return mapper.apply(name, field, type, realType);
                    } catch (Throwable e) {
                        throw new RuntimeException(e);
                    }
                })
                .collect(Collectors.toList());
    }

    private static List<FieldInfo> parseFields(Class<?> clazz) {
        return mapFields(clazz, (name, field, type, realType) -> parseField(name, field, realType));
    }

    private static FieldInfo parseField(String name, Field field, Class<?> realType) {
        Attribute[] attributes = field.getAnnotationsByType(Attribute.class);
        Map<String, Map<String, String>> attributesGroup = Stream.of(attributes)
                .groupBy(Attribute::name)
                .map((n, attrs) -> {
                    Map<String, String> map = Stream.ofAll(attrs)
                            .toMap(Attribute::group, Attribute::value)
                            .toJavaMap();
                    return Tuple.of(n, map);
                })
                .toJavaMap();

        return new FieldInfo(name, realType, attributesGroup);
    }

    private static boolean isStaticTyped(Field field) {
        int modifiers = field.getModifiers();
        Class<?> type = field.getType();
        return Modifier.isStatic(modifiers) &&
                Modifier.isPublic(modifiers) &&
                Typed.class.isAssignableFrom(type);
    }
}
