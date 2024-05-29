package com.daiyc.criteria.core.type.impl;

import com.daiyc.criteria.core.type.TypeConverter;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author daiyc
 */
public class LongConverter implements TypeConverter<Long> {
    @Override
    public Long from(Integer i) {
        return i.longValue();
    }

    @Override
    public Long from(Long l) {
        return l;
    }

    @Override
    public Long from(String s) {
        return Long.parseLong(s);
    }

    @Override
    public Long fromDate(Date date) {
        return date.getTime();
    }

    @Override
    public Long fromDateTime(Date date) {
        return date.getTime();
    }

    @Override
    public Long fromDouble(Double d) {
        return d.longValue();
    }

    @Override
    public Long fromFloat(Float f) {
        return f.longValue();
    }

    @Override
    public Long fromBigDecimal(BigDecimal b) {
        return b.longValue();
    }
}
