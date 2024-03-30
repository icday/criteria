package com.daiyc.criteria.core.schema;

import com.daiyc.criteria.core.schema.impl.MultiValueImpl;
import com.daiyc.criteria.core.schema.impl.ValueImpl;

/**
 * @author daiyc
 */
public class BookCriteria {
    protected final Value<Long> id = new ValueImpl<>("id", Long.class);

    protected final Value<String> name = new ValueImpl<>("name", String.class);

    protected final Value<Integer> category = new ValueImpl<>("category", Integer.class);

    protected final MultiValue<String> tags = new MultiValueImpl<>("tags", String.class);
}
