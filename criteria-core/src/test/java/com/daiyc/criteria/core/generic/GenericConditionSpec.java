package com.daiyc.criteria.core.generic;

import com.daiyc.criteria.core.common.BookSchema;
import com.daiyc.criteria.core.enums.*;
import com.daiyc.criteria.core.facade.ConditionMapper;
import com.daiyc.criteria.core.model.Condition;
import com.daiyc.criteria.core.schema.CriteriaSchema;
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
 * Generic Condition test cases
 *
 * @author daiyc
 */
public class GenericConditionSpec {
    private final ConditionMapper conditionMapper = ConditionMapper.DEFAULT_MAPPER;

    @Test
    public void testRead1() {
        Condition condition = read("query1.json").simplify();
        String str = condition.format();
        Assertions.assertEquals(
                "id > 100 AND name LIKE John AND publishedAt > 2024-01-02 00:10:00 AND tags contains (帅)",
                str);
    }

    @Test
    public void testRead11() {
        Condition condition = read("query1.json", BookSchema.getSchemaByBuilder()).simplify();
        String str = condition.format();
        Assertions.assertEquals(
                "id > 100 AND name LIKE John AND publishedAt > 2024-01-02 00:10:00 AND tags contains (帅)",
                str);
    }

    @SneakyThrows
    @Test
    public void testRead2() {
        Date date = DateUtils.parseDate("2024-04-24 10:11:12", "yyyy-MM-dd HH:mm:ss");

        Condition condition = read("query2.json").evaluate(date);

        Condition condition2 = PUBLISHED_AT.relativeAfterOrEqualsTo(-2, TimeUnit.DAYS, TimePrecision.DATE)
                .toCondition().evaluate(date).simplify();

        Assertions.assertEquals("publishedAt >= 2024-04-22 00:00:00", condition.format());
        Assertions.assertEquals("publishedAt >= 2024-04-22 00:00:00", condition2.format());
    }

    private Condition read(String filename) {
        return read(filename, conditionMapper.getSchema(BookSchema.class));
    }

    @SneakyThrows
    private Condition read(String filename, CriteriaSchema schema) {
        ClassLoader classLoader = this.getClass().getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream("generic/" + filename);

        String content = IOUtils.toString(inputStream, StandardCharsets.UTF_8);

        return conditionMapper.read(content, schema);
    }
}
