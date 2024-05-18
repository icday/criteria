package com.daiyc.criteria.core.schema;

import com.daiyc.criteria.core.builder.CriteriaBuilder;
import com.daiyc.criteria.core.model.Condition;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Objects;

import static com.daiyc.criteria.core.builder.CriteriaBuilders.*;
import static com.daiyc.criteria.core.common.BookSchema.*;

/**
 * @author daiyc
 */
public class BookQuerySpec {
    @Test
    public void testQuery() {
        CriteriaBuilder<?> builder = and(
                and(
                        ID.equalsTo(Objects::nonNull, 1L).or().greaterThan(100L).lessThan(Objects::isNull, 4L),
                        TAGS.containsAll("a", "b")
                ), or(
                        CATEGORY.in(1, 2, 3),
                        and(
                                and(
                                        and(and(NAME.like("xxxx").or())),
                                        ID.equalsTo(2L)
                                ),
                                NAME.equalsTo("yyyy")
                        )
                )
        );

        Condition criteria = builder.toCondition();

        String sql = "(id > 100 OR id = 1) AND tags contains (a, b) AND (category IN (1, 2, 3) OR name LIKE xxxx AND id = 2 AND name = yyyy)";

        Assertions.assertEquals(sql, criteria.simplify().format());
    }

    @Test
    public void testNotQuery() {
        CriteriaBuilder<?> builder = and(
                ID.equalsTo(1L).or().greaterThan(100L),
                TAGS.containsAll("a", "b")
        ).orWith(
                CATEGORY.in(1, 2, 3),
                and(
                        and(
                                and(and(not(NAME.like("xxxx")))),
                                ID.equalsTo(2L)
                        ),
                        NAME.equalsTo("yyyy")
                )
        );

        Condition criteria = builder.toCondition().simplify();

        String sql = "(id > 100 OR id = 1) AND tags contains (a, b) AND (category IN (1, 2, 3) OR !(name LIKE xxxx) AND id = 2 AND name = yyyy)";

        Assertions.assertEquals(sql, criteria.format());
    }

    @Test
    public void testSimplify1() {
        Condition criteria1 = and(
                not(NAME.like("xxx"))
        ).toCondition();
        Assertions.assertEquals("!(name LIKE xxx)", criteria1.format());
        Assertions.assertEquals("!(name LIKE xxx)", criteria1.simplify().format());

        Condition criteria2 = and(
                not(not(NAME.like("xxx")))
        ).toCondition();
        Assertions.assertEquals("!(!(name LIKE xxx))", criteria2.format());
        Assertions.assertEquals("name LIKE xxx", criteria2.simplify().format());
    }
}
