package com.daiyc.criteria.core.generic;

import com.daiyc.criteria.core.common.BookSchema;
import com.daiyc.criteria.core.enums.TimePrecision;
import com.daiyc.criteria.core.enums.TimeUnit;
import com.daiyc.criteria.core.facade.ConditionMapper;
import com.daiyc.criteria.core.facade.impl.DefaultConditionMapper;
import com.daiyc.criteria.core.model.Condition;
import com.daiyc.criteria.core.model.OperatorEnum;
import lombok.SneakyThrows;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Date;

import static com.daiyc.criteria.core.common.BookSchema.PUBLISHED_AT;

/**
 * @author daiyc
 */
public class GenericConditionSpec {
    private ConditionMapper conditionMapper = new DefaultConditionMapper(OperatorEnum.values());

    @Test
    public void testRead1() {
        Condition condition = read("/query1.json").simplify();
        String str = condition.format();
        Assertions.assertEquals(
                "id > 100 AND name LIKE John AND tags contains (帅)",
                str);
    }

    @SneakyThrows
    @Test
    public void testRead2() {
        Date date = DateUtils.parseDate("2024-04-24 10:11:12", "yyyy-MM-dd HH:mm:ss");

        Condition condition = read("query2.json").evaluate(date);

        Condition condition2 = PUBLISHED_AT.relativeAfterOrEqualsTo(-2, TimeUnit.DAYS, TimePrecision.DATE)
                .toCondition().evaluate(date).simplify();

        Assertions.assertEquals("publishedAt >= Mon Apr 22 00:00:00 CST 2024", condition.format());
        Assertions.assertEquals("publishedAt >= Mon Apr 22 00:00:00 CST 2024", condition2.format());
    }

    @SneakyThrows
    private Condition read(String fieldName) {
        ClassLoader classLoader = this.getClass().getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream("generic/" + fieldName);

        String content = IOUtils.toString(inputStream, StandardCharsets.UTF_8);

        return conditionMapper.read(content, BookSchema.class);
    }
}
