package com.daiyc.criteria.core.type;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author daiyc
 */
public interface TypeConverter<R> {
    default R convert(Object value) {
        if (value == null) {
            return null;
        }
        if (value instanceof Integer) {
            return from((Integer) value);
        } else if (value instanceof Double) {
            return fromDouble((Double) value);
        } else if (value instanceof Float) {
            return fromFloat((Float) value);
        } else if (value instanceof Long) {
            return from((Long) value);
        } else if (value instanceof BigDecimal) {
            return fromBigDecimal((BigDecimal) value);
        } else if (value instanceof String) {
            return from((String) value);
        } else if (value instanceof Date) {
            return fromDate((Date) value);
        }
        throw new IllegalArgumentException("Unsupported type: " + value.getClass());
    }

    default List<R> convertList(Collection<?> list) {
        if (list == null) {
            return null;
        }
        return list.stream().map(this::convert).collect(Collectors.toList());
    }

    R from(Integer i);

    R from(Long l);

    R from(String s);

    R fromDate(Date date);

    R fromDateTime(Date date);

    R fromDouble(Double d);

    R fromFloat(Float f);

    R fromBigDecimal(BigDecimal b);
}
