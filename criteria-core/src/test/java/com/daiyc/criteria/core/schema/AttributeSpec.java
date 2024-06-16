package com.daiyc.criteria.core.schema;

import com.daiyc.criteria.core.common.BookSchema;
import com.daiyc.criteria.core.facade.ConditionMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author daiyc
 */
public class AttributeSpec {
    private ConditionMapper conditionMapper = ConditionMapper.DEFAULT_MAPPER;

    @Test
    public void testFieldName() {
        CriteriaSchema schema = conditionMapper.getSchema(BookSchema.class);

        FieldInfo publishedAt = schema.getField("publishedAt");
        Assertions.assertEquals(publishedAt.getNameByGroup("es"), "published_at");
        Assertions.assertEquals(publishedAt.getNameByGroup("mysql"), "publishedAt");

        FieldInfo tags = schema.getField("tags");
        Assertions.assertEquals("tag_id", tags.getNameByGroup("mysql"));
        Assertions.assertEquals("tags", tags.getNameByGroup("es"));
    }
}
