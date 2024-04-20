package com.daiyc.criteria.core.generic;

import com.daiyc.criteria.core.model.Condition;
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
        Condition condition = cond.map().simplify();
        String str = condition.format();
        Assertions.assertEquals("id > 100 AND name LIKE John AND (age > 30 OR age < 18 OR tags contains (帅))",
                str);
    }
}
