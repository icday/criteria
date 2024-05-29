package com.daiyc.criteria.core.type.impl;

import com.daiyc.criteria.core.type.TypeConverter;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author daiyc
 */
public class FloatConverter implements TypeConverter<Float> {
    @Override
    public Float from(Integer i) {
        return i.floatValue();
    }

    @Override
    public Float from(Long l) {
        return l.floatValue();
    }

    @Override
    public Float from(String s) {
        return Float.valueOf(s);
    }

    @Override
    public Float fromDate(Date date) {
        return from(date.getTime());
    }

    @Override
    public Float fromDateTime(Date date) {
        return from(date.getTime());
    }

    @Override
    public Float fromDouble(Double d) {
        return d.floatValue();
    }

    @Override
    public Float fromFloat(Float f) {
        return f;
    }

    @Override
    public Float fromBigDecimal(BigDecimal b) {
        return b.floatValue();
    }
}
