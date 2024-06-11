package com.daiyc.criteria.core.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author daiyc
 */
@Getter
@RequiredArgsConstructor
public enum DataType {
    STRING(String.class),

    INT(Integer.class),

    LONG(Long.class),

    FLOAT(Float.class),

    DOUBLE(Double.class),

    BIG_DECIMAL(BigDecimal.class),

    DATE(Date.class),
    ;

    private final Class<?> clazz;

    private final static Map<Class<?>, DataType> CLASS_MAP = new HashMap<>();

    static {
        for (DataType type : values()) {
            CLASS_MAP.put(type.clazz, type);
        }
    }

    public static DataType of(Class<?> clazz) {
        DataType dataType = CLASS_MAP.get(clazz);
        assert dataType != null;
        return dataType;
    }
}
