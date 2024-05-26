package com.daiyc.criteria.core.generic;

import com.daiyc.criteria.core.common.BookSchema;
import com.daiyc.criteria.core.model.Condition;
import com.daiyc.criteria.core.schema.CriteriaSchema;
import com.daiyc.criteria.core.schema.SchemaFactory;
import lombok.SneakyThrows;
import org.apache.commons.lang3.time.DateUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.util.Date;

/**
 * @author daiyc
 */
public class GenericConditionSpec {
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
        String str = condition.format();

        Assertions.assertEquals("publishedAt >= Mon Apr 22 00:00:00 CST 2024", str);
    }

    private Condition read(String fieldName) {
        ClassLoader classLoader = this.getClass().getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream("generic/" + fieldName);
        ConditionReader reader = new ConditionReader();
        GenericCondition cond = reader.read(inputStream);

        CriteriaSchema bookSchema = SchemaFactory.create(BookSchema.class);
        return cond.map(bookSchema);
    }
}
