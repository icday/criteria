package com.daiyc.criteria.core.model.operator;

import com.daiyc.criteria.core.generic.GenericCriterion;
import com.daiyc.criteria.core.model.Operator;
import com.daiyc.criteria.core.schema.FieldInfo;
import com.daiyc.criteria.core.type.TypeConverter;
import com.daiyc.criteria.core.type.TypeConverterRegistry;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author daiyc
 */
@RequiredArgsConstructor
public abstract class BaseOperator implements Operator {
    @Getter
    protected final String symbol;

    protected <T> T getOperand(GenericCriterion genericCriterion, FieldInfo field) {
        TypeConverterRegistry typeConverterRegistry = TypeConverterRegistry.getInstance();
        TypeConverter<T> typeConverter = (TypeConverter<T>) typeConverterRegistry.get(field.getJavaType());
        return typeConverter.convert(genericCriterion.getValue());
    }

    protected <T> List<T> getOperands(GenericCriterion genericCriterion, FieldInfo field) {
        TypeConverterRegistry typeConverterRegistry = TypeConverterRegistry.getInstance();
        TypeConverter<T> typeConverter = (TypeConverter<T>) typeConverterRegistry.get(field.getJavaType());

        Object value = genericCriterion.getValue();
        if (!(value instanceof List)) {
            throw new IllegalArgumentException("The value of " + genericCriterion.getName() + " must be a list");
        }

        return Optional.ofNullable((List<?>) value)
                .orElse(Collections.emptyList())
                .stream()
                .map(typeConverter::convert)
                .collect(Collectors.toList());
    }
}
