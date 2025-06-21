package com.daiyc.criteria.core.annotations;

import java.lang.annotation.*;

/**
 * @author daiyc
 */
@Documented
@Target({ElementType.FIELD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(Attribute.List.class)
public @interface Attribute {
    String group() default "";

    String name();

    String value();

    @Documented
    @Target({ElementType.FIELD, ElementType.TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @interface List {
        Attribute[] value();
    }
}
