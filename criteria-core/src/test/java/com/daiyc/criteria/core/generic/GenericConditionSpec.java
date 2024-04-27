package com.daiyc.criteria.core.generic;

import com.daiyc.criteria.core.model.Condition;
import com.daiyc.criteria.core.schema.BookSchema;
import com.daiyc.criteria.core.schema.CriteriaSchema;
import com.daiyc.criteria.core.schema.SchemaFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.InputStream;

/**
 * @author daiyc
 */
public class GenericConditionSpec {
    @Test
    public void testRead() {
        // how to read resource file query1.json
        ClassLoader classLoader = this.getClass().getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream("generic/query1.json");
        ConditionReader reader = new ConditionReader();
        GenericCondition cond = reader.read(inputStream);

        CriteriaSchema bookSchema = SchemaFactory.create(BookSchema.class);
        Condition condition = cond.map(bookSchema).simplify();
        String str = condition.format();
        Assertions.assertEquals(
                "id > 100 AND name LIKE John AND tags contains (帅)",
                str);
    }
}
