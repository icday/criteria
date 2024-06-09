package com.daiyc.criteria.core.util;

import com.daiyc.criteria.core.annotations.Attribute;
import com.daiyc.criteria.core.schema.Attributes;
import io.vavr.Tuple;
import io.vavr.collection.Stream;

import java.util.Map;

/**
 * @author daiyc
 */
public abstract class AnnotationUtil {
    public static Attributes parseAttributes(Attribute[] attributes) {
        return parseAttributes(attributes, null);
    }

    public static Attributes parseAttributes(Attribute[] attributes, Attributes parent) {
        Map<String, Map<String, String>> data = Stream.of(attributes)
                .groupBy(Attribute::name)
                .map((n, attrs) -> {
                    Map<String, String> map = Stream.ofAll(attrs)
                            .toMap(Attribute::group, Attribute::value)
                            .toJavaMap();
                    return Tuple.of(n, map);
                })
                .toJavaMap();

        return new Attributes(data, parent);
    }
}
