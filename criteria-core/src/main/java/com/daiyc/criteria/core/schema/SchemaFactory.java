package com.daiyc.criteria.core.schema;

import com.daiyc.criteria.core.annotations.Attribute;
import com.daiyc.criteria.core.annotations.SchemaClass;
import com.daiyc.criteria.core.schema.impl.MultiValueImpl;
import com.daiyc.criteria.core.schema.impl.ValueImpl;
import com.daiyc.criteria.core.util.AnnotationUtil;
import com.google.common.base.CaseFormat;
import com.google.common.base.Converter;
import io.vavr.CheckedFunction4;
import org.apache.commons.lang3.reflect.FieldUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * @author daiyc
 */
public class SchemaFactory {
    private static final Converter<String, String> NAME_CONVERTER = CaseFormat.UPPER_UNDERSCORE.converterTo(CaseFormat.LOWER_CAMEL);

    private static final Map<Class<?>, CriteriaSchema> CACHE = new ConcurrentHashMap<>();

    private static final Map<Class<?>, Boolean> INITIALIZED_CLASSES = new ConcurrentHashMap<>();

    private static final Map<Class<?>, Class<?>> BEAN_SCHEMA_MAP = new ConcurrentHashMap<>();

    public static CriteriaSchema getByBean(Object bean) {
        if (bean == null) {
            return null;
        }
        Class<?> clazz = bean.getClass();
        Class<?> schemaType = BEAN_SCHEMA_MAP.computeIfAbsent(clazz, c -> {
            SchemaClass ann = clazz.getAnnotation(SchemaClass.class);
            if (ann == null) {
                return null;
            }
            return ann.value();
        });

        if (schemaType == null) {
            throw new IllegalArgumentException("Not specified schema bean type : " + clazz.getName());
        }

        return create(schemaType);
    }

    public static CriteriaSchema create(Class<?> clazz) {
        init(clazz);

        return CACHE.computeIfAbsent(clazz, c -> {
            Attribute[] annList = c.getAnnotationsByType(Attribute.class);
            Attributes attributes = AnnotationUtil.parseAttributes(annList);

            List<FieldInfo> fieldInfos = parseFields(c, attributes);
            return new CriteriaSchema(fieldInfos, attributes);
        });
    }

    public static void init(Class<?> clazz) {
        Boolean initialized = INITIALIZED_CLASSES.putIfAbsent(clazz, true);
        if (Boolean.TRUE.equals(initialized)) {
            return;
        }

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

    private static List<FieldInfo> parseFields(Class<?> clazz, Attributes parent) {
        return mapFields(clazz, (n, f, t, rt) -> parseField(n, f, t, rt, parent));
    }

    private static FieldInfo parseField(String name, Field field, Class<?> type, Class<?> realType, Attributes parent) {
        Attribute[] annList = field.getAnnotationsByType(Attribute.class);
        Attributes attributes = AnnotationUtil.parseAttributes(annList, parent);

        return new FieldInfo(name, realType, attributes);
    }

    private static boolean isStaticTyped(Field field) {
        int modifiers = field.getModifiers();
        Class<?> type = field.getType();
        return Modifier.isStatic(modifiers) &&
                Modifier.isPublic(modifiers) &&
                Typed.class.isAssignableFrom(type);
    }
}
