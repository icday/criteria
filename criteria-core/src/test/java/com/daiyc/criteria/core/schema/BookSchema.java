package com.daiyc.criteria.core.schema;

import com.daiyc.criteria.core.schema.impl.MultiValueImpl;
import com.daiyc.criteria.core.schema.impl.ValueImpl;
import lombok.Data;

/**
 * @author daiyc
 */
@Data
public class BookSchema {
    public static Value<Long> ID = new ValueImpl<>("id", Long.class);

    public static Value<String> NAME = new ValueImpl<>("name", String.class);

    public static Value<Integer> CATEGORY = new ValueImpl<>("category", Integer.class);

    public static MultiValue<String> TAGS = new MultiValueImpl<>("tags", String.class);
}
