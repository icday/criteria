package com.daiyc.criteria.core.enums;

import org.apache.commons.lang3.time.DateUtils;

import java.util.Calendar;
import java.util.Date;

/**
 * @author daiyc
 */
public enum TimePrecision {
    SECOND {
        @Override
        public Date apply(Date date) {
            return DateUtils.truncate(date, Calendar.SECOND);
        }
    },

    MINUTE {
        @Override
        public Date apply(Date date) {
            return DateUtils.truncate(date, Calendar.MINUTE);
        }
    },

    HOUR {
        @Override
        public Date apply(Date date) {
            return DateUtils.truncate(date, Calendar.HOUR);
        }
    },

    DATE {
        @Override
        public Date apply(Date date) {
            return DateUtils.truncate(date, Calendar.DATE);
        }
    },

    MONTH {
        @Override
        public Date apply(Date date) {
            return DateUtils.truncate(date, Calendar.MONTH);
        }
    },

    YEAR {
        @Override
        public Date apply(Date date) {
            return DateUtils.truncate(date, Calendar.YEAR);
        }
    },
    ;

    public abstract Date apply(Date date);

    public static Date apply(TimePrecision precision, Date date) {
        return precision == null ? date : precision.apply(date);
    }
}
