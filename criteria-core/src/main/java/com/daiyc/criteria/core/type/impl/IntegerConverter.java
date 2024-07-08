package com.daiyc.criteria.core.type.impl;

import com.daiyc.criteria.core.type.TypeConverter;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author daiyc
 */
public class IntegerConverter implements TypeConverter<Integer> {
    @Override
    public Integer from(Integer i) {
        return i;
    }

    @Override
    public Integer from(String s) {
        if (s == null || s.isEmpty()) {
            return null;
        }
        if (s.contains(".")) {
            return fromDouble(Double.parseDouble(s));
        }
        return Integer.parseInt(s);
    }

    @Override
    public Integer from(Long l) {
        return l.intValue();
    }

    @Override
    public Integer fromDate(Date date) {
        return (int) (date.getTime() / 1000);
    }

    @Override
    public Integer fromDateTime(Date date) {
        return (int) (date.getTime() / 1000);
    }

    @Override
    public Integer fromDouble(Double d) {
        return d.intValue();
    }

    @Override
    public Integer fromFloat(Float f) {
        return f.intValue();
    }

    @Override
    public Integer fromBigDecimal(BigDecimal b) {
        return b.intValueExact();
    }
}
