package com.daiyc.criteria.core.enums;

import com.google.common.base.CaseFormat;
import com.google.common.base.Converter;

/**
 * @author daiyc
 */
public enum PropertyNamingStrategy {
    /**
     * 小驼峰
     */
    LOWER_CAMEL_CASE(CaseFormat.LOWER_CAMEL),

    /**
     * 小写下划线
     */
    LOWER_UNDERSCORE(CaseFormat.LOWER_UNDERSCORE),

    /**
     * 大驼峰
     */
    UPPER_CAMEL_CASE(CaseFormat.UPPER_CAMEL),

    /**
     * 大写下划线
     */
    UPPER_UNDERSCORE(CaseFormat.UPPER_UNDERSCORE),

    ;

    PropertyNamingStrategy(CaseFormat caseFormat) {
        converter = CaseFormat.LOWER_CAMEL.converterTo(caseFormat);
    }

    private final Converter<String, String> converter;

    public String convert(String from) {
        return converter.convert(from);
    }

    public static final String LOWER_CAMEL_CASE_NAME = "LOWER_CAMEL_CASE";
    public static final String LOWER_UNDERSCORE_NAME = "LOWER_UNDERSCORE";
    public static final String UPPER_CAMEL_CASE_NAME = "UPPER_CAMEL_CASE";
    public static final String UPPER_UNDERSCORE_NAME = "UPPER_UNDERSCORE";
}
