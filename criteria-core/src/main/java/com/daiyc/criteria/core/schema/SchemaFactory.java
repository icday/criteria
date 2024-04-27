package com.daiyc.criteria.core.schema;

import com.daiyc.criteria.core.annotations.Alias;
import com.daiyc.criteria.core.schema.impl.MultiValueImpl;
import com.daiyc.criteria.core.schema.impl.ValueImpl;
import com.google.common.base.CaseFormat;
import com.google.common.base.Converter;
import io.vavr.CheckedFunction4;
import org.apache.commons.lang3.reflect.FieldUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author daiyc
 */
public class SchemaFactory {
    private static final Converter<String, String> NAME_CONVERTER = CaseFormat.UPPER_UNDERSCORE.converterTo(CaseFormat.LOWER_CAMEL);

    private static final Map<Class<?>, CriteriaSchema> CACHE = new ConcurrentHashMap<>();

    public static CriteriaSchema create(Class<?> clazz) {
        return CACHE.computeIfAbsent(clazz, c -> {
            init(c);
            List<FieldInfo> fieldInfos = parseFields(c);
            return new CriteriaSchema(fieldInfos);
        });
    }

    private static void init(Class<?> clazz) {
        mapFields(clazz, (name, field, type, realType) -> {
            if (Value.class.isAssignableFrom(type)) {
                ValueImpl<?> value = new ValueImpl<>(name, realType);
                field.set(null, value);
                return value;
            } else {
                MultiValueImpl<?> multiValue = new MultiValueImpl<>(name, realType);
                field.set(null, multiValue);
                return multiValue;
            }
        });
    }

    private static <T> List<T> mapFields(Class<?> clazz, CheckedFunction4<String, Field, Class<?>, Class<?>, T> mapper) {
        return FieldUtils.getAllFieldsList(clazz).stream()
                .filter(SchemaFactory::isStaticTyped)
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
        return mapFields(clazz, SchemaFactory::parseField);
    }

    private static FieldInfo parseField(String name, Field field, Class<?> type, Class<?> realType) {
        Alias.List aliases = field.getAnnotation(Alias.List.class);
        Map<String, String> aliasMap = Optional.ofNullable(aliases)
                .map(Alias.List::value)
                .map(Stream::of)
                .orElse(Stream.empty())
                .collect(Collectors.toMap(Alias::group, Alias::name));

        return new FieldInfo(name, realType, aliasMap);
    }

    private static boolean isStaticTyped(Field field) {
        int modifiers = field.getModifiers();
        Class<?> type = field.getType();
        return Modifier.isStatic(modifiers) &&
                Modifier.isPublic(modifiers) &&
                Typed.class.isAssignableFrom(type);
    }
}
