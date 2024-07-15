package com.daiyc.criteria.core.condition;

import com.daiyc.criteria.core.enums.TimePrecision;
import com.daiyc.criteria.core.enums.TimeUnit;
import com.daiyc.criteria.core.model.Condition;
import lombok.SneakyThrows;
import org.apache.commons.lang3.time.DateUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static com.daiyc.criteria.core.builder.CriteriaBuilders.and;
import static com.daiyc.criteria.core.common.BookSchema.PUBLISHED_AT;
import static com.daiyc.criteria.core.common.BookSchema.TAGS;

/**
 * @author daiyc
 */
public class RelativeTimeConditionSpec {

    @SneakyThrows
    @Test
    public void testRelativeTime() {
        Date date = DateUtils.parseDate("2024-04-24 10:11:12", "yyyy-MM-dd HH:mm:ss");
        Condition condition = and(
                PUBLISHED_AT.relativeAfterOrEqualsTo(-2, TimeUnit.DAYS, TimePrecision.DATE),
                TAGS.contains("java")
        ).toCondition();
        condition = condition.evaluate(date);
        Assertions.assertEquals("publishedAt >= 2024-04-22 00:00:00 AND tags contains (java)", condition.format(), "相对时间条件");
    }
}
