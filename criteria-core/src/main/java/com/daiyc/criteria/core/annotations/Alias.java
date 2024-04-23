package com.daiyc.criteria.core.annotations;

import java.lang.annotation.*;

/**
 * @author daiyc
 */
@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(Alias.List.class)
public @interface Alias {
    /**
     * 组名
     */
    String group();

    /**
     * 对应字段名
     */
    String name();

    @Documented
    @Target(ElementType.FIELD)
    @Retention(RetentionPolicy.RUNTIME)
    @interface List {
        Alias[] value();
    }
}
