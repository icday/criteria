package com.daiyc.criteria.core.common;

import com.daiyc.criteria.core.annotations.Attribute;
import com.daiyc.criteria.core.enums.DataType;
import com.daiyc.criteria.core.enums.PropertyNamingStrategy;
import com.daiyc.criteria.core.enums.SchemaAttribute;
import com.daiyc.criteria.core.schema.CriteriaSchema;
import com.daiyc.criteria.core.schema.MultiValue;
import com.daiyc.criteria.core.schema.SchemaFactory;
import com.daiyc.criteria.core.schema.Value;
import lombok.Data;

import java.util.Date;

/**
 * @author daiyc
 */
@Data
@Attribute(group = "es", name = SchemaAttribute.NAMING_STRATEGY, value = PropertyNamingStrategy.LOWER_UNDERSCORE_NAME)
public class BookSchema {
    public static Value<Long> ID;

    public static Value<String> NAME;

    public static Value<Integer> CATEGORY;

    @Attribute(group = "mysql", name = SchemaAttribute.NAME, value = "tag_id")
    public static MultiValue<String> TAGS;

    @Attribute(group = "es", name = SchemaAttribute.PATTERN, value = "yyyy-MM-dd")
    public static Value<Date> PUBLISHED_AT;

    static {
        SchemaFactory.init(BookSchema.class);
    }

    public static CriteriaSchema getSchemaByBuilder() {
        return CriteriaSchema.builder()
                .field("id", DataType.LONG)
                .field("name", DataType.STRING)
                .field("category", DataType.INT)
                .field("tags", DataType.STRING, fb -> {
                    fb.attribute(SchemaAttribute.NAME, "mysql", "tag_id");
                })
                .field("publishedAt", fb -> {
                    fb.type(DataType.DATE)
                            .attribute(SchemaAttribute.PATTERN, "es", "yyyy-MM-dd");
                })
                .attribute(SchemaAttribute.NAMING_STRATEGY, "es", PropertyNamingStrategy.LOWER_UNDERSCORE_NAME)
                .build();
    }
}
