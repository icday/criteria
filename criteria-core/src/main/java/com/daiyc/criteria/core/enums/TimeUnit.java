package com.daiyc.criteria.core.enums;

import org.apache.commons.lang3.time.DateUtils;

import java.util.Date;

/**
 * @author daiyc
 */
public enum TimeUnit {
    SECONDS {
        @Override
        public Date apply(Date date, int delta) {
            return DateUtils.addSeconds(date, delta);
        }
    },

    MINUTES {
        @Override
        public Date apply(Date date, int delta) {
            return DateUtils.addMinutes(date, delta);
        }
    },

    HOURS {
        @Override
        public Date apply(Date date, int delta) {
            return DateUtils.addHours(date, delta);
        }
    },

    DAYS {
        @Override
        public Date apply(Date date, int delta) {
            return DateUtils.addDays(date, delta);
        }
    },

    WEEKS {
        @Override
        public Date apply(Date date, int delta) {
            return DateUtils.addWeeks(date, delta);
        }
    },

    MONTHS {
        @Override
        public Date apply(Date date, int delta) {
            return DateUtils.addMonths(date, delta);
        }
    },

    YEARS {
        @Override
        public Date apply(Date date, int delta) {
            return DateUtils.addYears(date, delta);
        }
    }
    ;

    static final long C0 = 1L;
    static final long C1 = C0 * 1000L;
    static final long C2 = C1 * 1000L;
    static final long C3 = C2 * 1000L;
    static final long C4 = C3 * 60L;
    static final long C5 = C4 * 60L;
    static final long C6 = C5 * 24L;

    public abstract Date apply(Date date, int delta);

    public static Date apply(TimeUnit unit, Date date, int delta) {
        return unit == null ? date : unit.apply(date, delta);
    }
}
