package com.daiyc.criteria.core.type.impl;

import com.daiyc.criteria.core.type.TypeConverter;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author daiyc
 */
public class DoubleConverter implements TypeConverter<Double> {
    @Override
    public Double from(Integer i) {
        return i.doubleValue();
    }

    @Override
    public Double from(Long l) {
        return l.doubleValue();
    }

    @Override
    public Double from(String s) {
        return Double.valueOf(s);
    }

    @Override
    public Double fromDate(Date date) {
        return (double) date.getTime();
    }

    @Override
    public Double fromDateTime(Date date) {
        return (double) date.getTime();
    }

    @Override
    public Double fromDouble(Double d) {
        return d;
    }

    @Override
    public Double fromFloat(Float f) {
        return f.doubleValue();
    }

    @Override
    public Double fromBigDecimal(BigDecimal b) {
        return b.doubleValue();
    }
}
