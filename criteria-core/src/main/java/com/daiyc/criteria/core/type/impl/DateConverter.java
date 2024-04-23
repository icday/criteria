package com.daiyc.criteria.core.type.impl;

import com.daiyc.criteria.core.type.TypeConverter;
import lombok.SneakyThrows;
import org.apache.commons.lang3.time.DateUtils;

import java.util.Date;

/**
 * @author daiyc
 */
public class DateConverter implements TypeConverter<Date> {
    @Override
    public Date from(Integer i) {
        return new Date(i * 1000);
    }

    @Override
    public Date from(Long l) {
        return new Date(l);
    }

    @SneakyThrows
    @Override
    public Date from(String s) {
        return DateUtils.parseDate(s, "yyyy-MM-dd HH:mm:ss");
    }

    @Override
    public Date fromDate(Date date) {
        return date;
    }

    @Override
    public Date fromDateTime(Date date) {
        return date;
    }
}
