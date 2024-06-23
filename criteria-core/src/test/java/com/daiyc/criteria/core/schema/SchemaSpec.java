package com.daiyc.criteria.core.schema;

import com.daiyc.criteria.core.common.BookSchema;
import com.daiyc.criteria.core.facade.ConditionMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author daiyc
 */
public class SchemaSpec {
    private final ConditionMapper conditionMapper = ConditionMapper.DEFAULT_MAPPER;

    @Test
    public void testFieldName() {
        CriteriaSchema schema = conditionMapper.getSchema(BookSchema.class);

        testSchema(schema);
    }

    @Test
    public void testSchemaBuilder() {
        CriteriaSchema schema = BookSchema.getSchemaByBuilder();
        testSchema(schema);

        assertSchemaEquals(conditionMapper.getSchema(BookSchema.class), schema);
    }

    @Test
    public void testSchemaToJson() {
        CriteriaSchema origSchema = conditionMapper.getSchema(BookSchema.class);

        String schemaJson = conditionMapper.toSchemaJson(origSchema);

        CriteriaSchema schema = conditionMapper.toSchema(schemaJson);

        assertSchemaEquals(origSchema, schema);
    }

    protected void assertSchemaEquals(CriteriaSchema schema1, CriteriaSchema schema2) {
        Assertions.assertEquals(schema1, schema2);

        Assertions.assertEquals(
                "published_at",
                schema1.getField("publishedAt").getNameByGroup("es")
        );

        Assertions.assertEquals(
                schema1.getField("publishedAt").getNameByGroup("es"),
                schema2.getField("publishedAt").getNameByGroup("es")
        );

        Assertions.assertEquals(
                "id",
                schema1.getField("id").getNameByGroup("es")
        );
    }

    protected void testSchema(CriteriaSchema schema) {
        FieldInfo publishedAt = schema.getField("publishedAt");

        Assertions.assertEquals(publishedAt.getNameByGroup("es"), "published_at");
        Assertions.assertEquals(publishedAt.getNameByGroup("mysql"), "publishedAt");

        FieldInfo tags = schema.getField("tags");
        Assertions.assertEquals("tag_id", tags.getNameByGroup("mysql"));
        Assertions.assertEquals("tags", tags.getNameByGroup("es"));
    }
}
