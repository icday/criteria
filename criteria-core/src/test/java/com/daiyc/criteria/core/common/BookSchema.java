package com.daiyc.criteria.core.common;

import com.daiyc.criteria.core.schema.MultiValue;
import com.daiyc.criteria.core.schema.SchemaFactory;
import com.daiyc.criteria.core.schema.Value;
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
        SchemaFactory.init(BookSchema.class);
    }
}
