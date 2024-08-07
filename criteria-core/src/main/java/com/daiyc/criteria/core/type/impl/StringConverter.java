package com.daiyc.criteria.core.type.impl;

import com.daiyc.criteria.core.type.TypeConverter;
import org.apache.commons.lang3.time.DateFormatUtils;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author daiyc
 */
public class StringConverter implements TypeConverter<String> {
    @Override
    public String from(Integer i) {
        return i.toString();
    }

    @Override
    public String from(Long l) {
        return l.toString();
    }

    @Override
    public String from(String s) {
        return s;
    }

    @Override
    public String fromDate(Date date) {
        return DateFormatUtils.format(date, "yyyy-MM-dd");
    }

    @Override
    public String fromDateTime(Date date) {
        return DateFormatUtils.format(date, "yyyy-MM-dd HH:mm:ss");
    }

    @Override
    public String fromDouble(Double d) {
        return d.toString();
    }

    @Override
    public String fromFloat(Float f) {
        return f.toString();
    }

    @Override
    public String fromBigDecimal(BigDecimal b) {
        return b.toString();
    }
}
