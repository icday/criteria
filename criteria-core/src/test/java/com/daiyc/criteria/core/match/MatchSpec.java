package com.daiyc.criteria.core.match;

import com.daiyc.criteria.core.common.Book;
import com.daiyc.criteria.core.common.BookSchema;
import com.daiyc.criteria.core.model.Condition;
import com.daiyc.criteria.core.schema.CriteriaSchema;
import com.daiyc.criteria.core.schema.SchemaFactory;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static com.daiyc.criteria.core.common.BookSchema.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * @author daiyc
 */
public class MatchSpec {

    @Test
    public void testMatch1() {
        Book book = new Book()
                .setId(100L)
                .setName("论持久战")
                .setTags(Arrays.asList("政治", "军事"))
                .setCategory(20);

        CriteriaSchema schema = SchemaFactory.create(BookSchema.class);

        Condition condition1 = ID.greaterThan(10L).toCondition();
        Condition condition2 = NAME.equalsTo("持久战").or().like("战").toCondition();
        Condition condition3 = NAME.equalsTo("持久战").like("战").toCondition();
        Condition condition4 = TAGS.containsAny("政治", "农业").toCondition();
        Condition condition5 = TAGS.containsAll("政治", "农业").toCondition();
        Condition condition6 = TAGS.containsAll("政治", "军事").toCondition();

        assertAll(
                () -> assertTrue(condition1.match(book, schema))
                , () -> assertTrue(condition2.match(book, schema))
                , () -> assertFalse(condition3.match(book, schema))
                , () -> assertTrue(condition4.match(book, schema))
                , () -> assertFalse(condition5.match(book, schema))
                , () -> assertTrue(condition6.match(book, schema))
        );
    }
}
