package com.daiyc.criteria.core.generic;

import com.daiyc.criteria.core.enums.TimePrecision;
import com.daiyc.criteria.core.enums.TimeUnit;
import com.daiyc.criteria.core.model.Condition;
import com.daiyc.criteria.core.model.CriterionFactory;
import com.daiyc.criteria.core.model.OperandNum;
import com.daiyc.criteria.core.model.OperatorEnum;
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

    private String timeUnit;

    private String timePrecision;

    @Override
    public Condition map(CriteriaSchema schema) {
        OperatorEnum op = OperatorEnum.symbolOf(this.getOperator());
        FieldInfo field = schema.getField(name);
        if (field == null) {
            throw new IllegalArgumentException("Unknown field: "+ name);
        }

        TypeConverterRegistry registry = TypeConverterRegistry.getInstance();

        if (op.isRelativeTimeOperator()) {
            return CriterionFactory.relativeTime(this.name, op, (Integer) registry.get(Integer.class).convert(value)
                    , TimeUnit.valueOf(timeUnit.toUpperCase())
                    , timePrecision != null ? TimePrecision.valueOf(timePrecision.toUpperCase()) : null
            );
        }

        Class<?> targetType = field.getType();
        TypeConverter<?> typeConverter = registry.get(targetType);

        if (typeConverter == null) {
            throw new IllegalArgumentException("Unknown type: "+ targetType.getName());
        }

        OperandNum operandNum = op.getOperandNum();
        if (operandNum == OperandNum.DOUBLE || operandNum == OperandNum.MORE) {
            assert this.getValue() instanceof List;
            return CriterionFactory.create(this.getName(), op, typeConverter.convertList((List<?>) this.getValue()));
        }

        if (operandNum == OperandNum.SINGLE) {
            assert this.getValue() != null;
            return CriterionFactory.create(this.getName(), op, typeConverter.convert(this.getValue()));
        }

        return CriterionFactory.create(this.getName(), op);
    }
}
