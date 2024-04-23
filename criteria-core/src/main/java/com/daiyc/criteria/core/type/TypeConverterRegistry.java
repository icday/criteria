package com.daiyc.criteria.core.type;

import com.daiyc.criteria.core.type.impl.DateConverter;
import com.daiyc.criteria.core.type.impl.IntegerConverter;
import com.daiyc.criteria.core.type.impl.LongConverter;
import com.daiyc.criteria.core.type.impl.StringConverter;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author daiyc
 */
public class TypeConverterRegistry {
    private static final TypeConverterRegistry INSTANCE = new TypeConverterRegistry();

    public static TypeConverterRegistry getInstance() {
        return INSTANCE;
    }

    private final Map<Class<?>, TypeConverter<?>> converterMap;

    public TypeConverterRegistry() {
        converterMap = new HashMap<>();
        init();
    }

    protected void init() {
        converterMap.put(String.class, new StringConverter());
        converterMap.put(Integer.class, new IntegerConverter());
        converterMap.put(Date.class, new DateConverter());
        converterMap.put(Long.class, new LongConverter());
    }

    public TypeConverter<?> get(Class<?> targetType) {
        return converterMap.get(targetType);
    }
}
