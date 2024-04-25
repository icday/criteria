package com.daiyc.criteria.core.schema;

import com.daiyc.criteria.core.annotations.Alias;
import com.google.common.base.CaseFormat;
import com.google.common.base.Converter;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author daiyc
 */
@Data
@RequiredArgsConstructor
public class CriteriaSchema {
    private final List<FieldInfo> fields;

    private static final Converter<String, String> NAME_CONVERTER = CaseFormat.UPPER_UNDERSCORE.converterTo(CaseFormat.LOWER_CAMEL);

    public CriteriaSchema(Class<?> schemaClass) {
        this.fields = parseFields(schemaClass);
    }

    public FieldInfo getField(String name) {
        return fields.stream()
                .filter(field -> field.getName().equals(name))
                .findFirst()
                .orElse(null);
    }

    private static boolean isStaticTyped(Field field) {
        int modifiers = field.getModifiers();
        Class<?> type = field.getType();
        return Modifier.isStatic(modifiers) &&
                Modifier.isPublic(modifiers) &&
                Typed.class.isAssignableFrom(type);
    }

    private static List<FieldInfo> parseFields(Class<?> clazz) {
        return Arrays.stream(clazz.getDeclaredFields())
                .filter(CriteriaSchema::isStaticTyped)
                .map(field -> {
                    try {
                        Typed<?> typed = (Typed<?>) field.get(null);
                        return parseField(field, typed);
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                })
                .collect(Collectors.toList());
    }

    private static FieldInfo parseField(Field field, Typed<?> typed) {
        String fieldName = NAME_CONVERTER.convert(field.getName());
        Alias.List aliases = field.getAnnotation(Alias.List.class);
        Map<String, String> aliasMap = Optional.ofNullable(aliases)
                .map(Alias.List::value)
                .map(Stream::of)
                .orElse(Stream.empty())
                .collect(Collectors.toMap(Alias::group, Alias::name));

        return new FieldInfo(fieldName, typed.getType(), aliasMap);
    }
}
