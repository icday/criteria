package com.daiyc.criteria.core.model;

import com.daiyc.criteria.core.transform.TransformContext;
import com.daiyc.criteria.core.transform.Transformer;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.Date;
import java.util.List;
import java.util.function.Function;

/**
 * @author daiyc
 */
@Data
@RequiredArgsConstructor
public class RelativeTimeCriterion implements Criterion<Date> {
    private final String fieldName;

    private final Operator operator;

    private final Function<Date, Date> singleValueLazy;

    private final Function<Date, List<Date>> listValueLazy;

    @Override
    public <T> T transform(Transformer<T> transformer, TransformContext ctx) {
        return transformer.transform(this, ctx);
    }

    @Override
    public List<Date> getListValue() {
        throw new UnsupportedOperationException("Invalid listValue for RelativeTimeCriterion");
    }

    @Override
    public Date getSingleValue() {
        throw new UnsupportedOperationException("Invalid singleValue for RelativeTimeCriterion");
    }

    @Override
    public String getListValueJson() {
        throw new UnsupportedOperationException("Invalid listValueJson for RelativeTimeCriterion");
    }

    public GeneralCriterion<Date> evaluate(Date value) {
        return doEval(value);
    }

    protected GeneralCriterion<Date> doEval(Date value) {
        OperatorEnum operatorEnum = OperatorEnum.symbolOf(operator.getSymbol());
        return new GeneralCriterion<>(fieldName, operatorEnum.getGeneralOperator(),
                singleValueLazy == null ? null : singleValueLazy.apply(value),
                listValueLazy == null ? null : listValueLazy.apply(value));
    }
}
