package com.daiyc.criteria.core.schema;

import lombok.Data;

/**
 * @author daiyc
 */
@Data
public class BookSchema {
    public static Value<Long> ID;

    public static Value<String> NAME;

    public static Value<Integer> CATEGORY;

    public static MultiValue<String> TAGS;

    static {
        SchemaFactory.create(BookSchema.class);
    }
}
