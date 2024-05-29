package com.daiyc.criteria.core.type.impl;

import com.daiyc.criteria.core.type.TypeConverter;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author daiyc
 */
public class BigDecimalConverter implements TypeConverter<BigDecimal> {
    @Override
    public BigDecimal from(Integer i) {
        return new BigDecimal(i);
    }

    @Override
    public BigDecimal from(Long l) {
        return new BigDecimal(l);
    }

    @Override
    public BigDecimal from(String s) {
        return new BigDecimal(s);
    }

    @Override
    public BigDecimal fromDate(Date date) {
        return from(date.getTime());
    }

    @Override
    public BigDecimal fromDateTime(Date date) {
        return from(date.getTime());
    }

    @Override
    public BigDecimal fromDouble(Double d) {
        return new BigDecimal(d);
    }

    @Override
    public BigDecimal fromFloat(Float f) {
        return new BigDecimal(f);
    }

    @Override
    public BigDecimal fromBigDecimal(BigDecimal b) {
        return b;
    }
}
