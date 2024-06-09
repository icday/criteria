package com.daiyc.criteria.mybatis;

import com.daiyc.criteria.core.annotations.Attribute;
import com.daiyc.criteria.core.enums.PropertyNamingStrategy;
import com.daiyc.criteria.core.enums.SchemaAttribute;
import com.daiyc.criteria.core.schema.MultiValue;
import com.daiyc.criteria.core.schema.SchemaFactory;
import com.daiyc.criteria.core.schema.Value;
import lombok.Data;

/**
 * @author daiyc
 */
@Data
@Attribute(group ="sql", name = SchemaAttribute.NAMING_STRATEGY, value = PropertyNamingStrategy.LOWER_UNDERSCORE_NAME)
public class BookSchema {
    public static Value<Long> ID;

    @Attribute(group ="sql", name = SchemaAttribute.NAME, value = "book_name")
    public static Value<String> NAME;

    public static Value<Integer> CATEGORY;

    public static Value<String> PUBLISHED_AT;

    public static MultiValue<String> TAGS;

    static {
        SchemaFactory.init(BookSchema.class);
    }
}
