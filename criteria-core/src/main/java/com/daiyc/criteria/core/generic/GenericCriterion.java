package com.daiyc.criteria.core.generic;

import com.daiyc.criteria.core.model.*;
import com.daiyc.criteria.core.schema.CriteriaSchema;
import com.daiyc.criteria.core.schema.FieldInfo;
import com.daiyc.criteria.core.type.TypeConverter;
import com.daiyc.criteria.core.type.TypeConverterRegistry;
import lombok.Data;

import java.util.List;

/**
 * @author daiyc
 */
@Data
public class GenericCriterion implements GenericCondition {
    private String name;

    private String operator;

    private Object value;

    @Override
    public Condition map(CriteriaSchema schema) {
        Operator op = OperatorEnum.symbolOf(this.getOperator());
        FieldInfo field = schema.getField(name);
        if (field == null) {
            throw new IllegalArgumentException("Unknown field: "+ name);
        }

        Class<?> targetType = field.getType();
        TypeConverterRegistry registry = TypeConverterRegistry.getInstance();
        TypeConverter<?> typeConverter = registry.get(targetType);

        if (typeConverter == null) {
            throw new IllegalArgumentException("Unknown type: "+ targetType.getName());
        }

        OperandNum operandNum = op.getOperandNum();
        if (operandNum == OperandNum.DOUBLE || operandNum == OperandNum.MORE) {
            assert this.getValue() instanceof List;
            return new Criterion<>(this.getName(), op, typeConverter.convertList((List<?>) this.getValue()));
        }

        if (operandNum == OperandNum.SINGLE) {
            assert this.getValue() != null;
            return new Criterion<>(this.getName(), op, typeConverter.convert(this.getValue()));
        }

        return new Criterion<>(this.getName(), op);
    }
}
