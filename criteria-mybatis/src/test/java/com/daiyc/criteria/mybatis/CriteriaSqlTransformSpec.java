package com.daiyc.criteria.mybatis;

import com.daiyc.criteria.core.builder.CriteriaBuilder;
import com.daiyc.criteria.core.model.Condition;
import com.daiyc.criteria.core.schema.CriteriaSchema;
import com.daiyc.criteria.core.schema.SchemaFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static com.daiyc.criteria.core.builder.CriteriaBuilders.and;
import static com.daiyc.criteria.mybatis.BookSchema.*;

/**
 * @author daiyc
 */
public class CriteriaSqlTransformSpec {
    private final CriteriaSqlTransformer sqlTransformer = new CriteriaSqlTransformer("criteria");

    @Test
    public void testProperty() {
        CriteriaSchema schema = SchemaFactory.create(BookSchema.class);

        Condition condition = PUBLISHED_AT.equalsTo("2000-01-01 00:00:00").toCondition();

        String str = condition.transform(sqlTransformer, schema);
        Assertions.assertEquals("`published_at` = #{criteria.singleValue}", str);
    }

    @Test
    public void testSqlTrans() {
        CriteriaSchema schema = SchemaFactory.create(BookSchema.class);

        Condition cond = and(
                NAME.like("x")
        ).toCondition();

        String str = cond.transform(sqlTransformer, schema);
        Assertions.assertEquals("`book_name` LIKE #{criteria.singleValue}", str);
    }

    @Test
    public void testTransform() {
        CriteriaSchema schema = SchemaFactory.create(BookSchema.class);

        CriteriaBuilder<?> builder = and(
                ID.equalsTo(1L).or().greaterThan(100L)
        ).orWith(
                CATEGORY.in(1, 2, 3),
                and(
                        and(
                                and(and(NAME.like("xxxx").or())),
                                ID.equalsTo(2L)
                        ),
                        NAME.equalsTo("yyyy")
                )
        );

        Condition criteria = builder.toCondition().simplify();

        CriteriaSqlTransformer sqlTransformer = new CriteriaSqlTransformer("criteria");

        String sql = "(`id` > #{criteria.children[0].children[0].singleValue} OR `id` = #{criteria.children[0].children[1].singleValue})" +
                    " AND (`category` IN (#{criteria.children[1].children[0].listValue[0]}, #{criteria.children[1].children[0].listValue[1]}, #{criteria.children[1].children[0].listValue[2]})" +
                " OR `book_name` LIKE #{criteria.children[1].children[1].children[0].singleValue}" +
                    " AND `id` = #{criteria.children[1].children[1].children[1].singleValue}" +
                    " AND `book_name` = #{criteria.children[1].children[1].children[2].singleValue})";

        String res = criteria.transform(sqlTransformer, schema);
        Assertions.assertEquals(sql, res);
    }
}
