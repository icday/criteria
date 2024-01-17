package com.daiyc.criteria.core.schema;

import com.daiyc.criteria.core.builder.CriteriaBuilder;
import com.daiyc.criteria.core.model.Criteria;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static com.daiyc.criteria.core.builder.CriteriaBuilders.and;
import static com.daiyc.criteria.core.schema.BookSchema.*;

/**
 * @author daiyc
 */
public class BookQuerySpec {
    @Test
    public void testQuery() {
        CriteriaBuilder<?> builder = and(
                ID.equalsTo(1L).or().greaterThan(100L),
                TAGS.containsAll("a", "b")
        ).or(
                CATEGORY.in(1, 2, 3),
                and(
                        and(
                                and(and(NAME.like("xxxx").or())),
                                ID.equalsTo(2L)
                        ),
                        NAME.equalsTo("yyyy")
                )
        );

        Criteria criteria = builder.toCriteria();

        String sql = "tags contains (a, b) AND ((id > 100) OR (id = 1)) AND ((category IN (1, 2, 3)) OR (name LIKE xxxx AND id = 2 AND name = yyyy))";

        Assertions.assertEquals(sql, criteria.toString());
    }
}
