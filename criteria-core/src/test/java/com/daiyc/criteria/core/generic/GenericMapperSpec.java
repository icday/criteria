package com.daiyc.criteria.core.generic;

import com.daiyc.criteria.core.common.BookSchema;
import com.daiyc.criteria.core.enums.TimePrecision;
import com.daiyc.criteria.core.enums.TimeUnit;
import com.daiyc.criteria.core.facade.ConditionMapper;
import com.daiyc.criteria.core.model.Condition;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static com.daiyc.criteria.core.builder.CriteriaBuilders.and;
import static com.daiyc.criteria.core.common.BookSchema.ID;
import static com.daiyc.criteria.core.common.BookSchema.PUBLISHED_AT;

/**
 * @author daiyc
 */
public class GenericMapperSpec {
    private final ConditionMapper conditionMapper = ConditionMapper.DEFAULT_MAPPER;

    @Test
    public void testToJson() {
        Condition condition = and(
                ID.greaterThan(100L).lessThan(1000L),
                PUBLISHED_AT.relativeAfter(1, TimeUnit.DAYS, TimePrecision.DATE)
        ).toCondition();

        String json = conditionMapper.toJson(condition);
        Condition conditionParsed = conditionMapper.read(json, BookSchema.class);
        String formattedStr = conditionParsed.format();

        Assertions.assertEquals("id > 100 AND id < 1000 AND publishedAt > #{date(DATE)} + 1(DAYS)", formattedStr);
    }
}
