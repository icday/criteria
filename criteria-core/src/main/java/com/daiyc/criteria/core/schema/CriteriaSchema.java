package com.daiyc.criteria.core.schema;

import com.google.common.base.CaseFormat;
import com.google.common.base.Converter;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author daiyc
 */
@Data
@RequiredArgsConstructor
public class CriteriaSchema {
    private final List<FieldInfo> fields;

    private final Map<String, FieldInfo> fieldInfoMap;

    private static final Converter<String, String> NAME_CONVERTER = CaseFormat.UPPER_UNDERSCORE.converterTo(CaseFormat.LOWER_CAMEL);

    public CriteriaSchema(List<FieldInfo> fields) {
        this.fields = fields;
        this.fieldInfoMap = fields.stream()
                .collect(Collectors.toMap(FieldInfo::getName, s -> s));
    }

    public FieldInfo getField(String name) {
        return fieldInfoMap.get(name);
    }
}
