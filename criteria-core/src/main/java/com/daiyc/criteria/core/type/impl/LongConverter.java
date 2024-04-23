package com.daiyc.criteria.core.type.impl;

import com.daiyc.criteria.core.type.TypeConverter;

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
}
