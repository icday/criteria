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

    private final Lazy<Date> singleValueLazy;

    private final Lazy<List<Date>> listValueLazy;

    private GeneralCriterion<Date> evaluatedValue;

    @RequiredArgsConstructor
    public static class Lazy<T> {
        private final Function<Date, T> fn;

        private T value;

        public T evaluate(Date date) {
            if (value != null) {
                return value;
            }

            return doEval(date);
        }

        protected synchronized T doEval(Date date) {
            if (value != null) {
                return value;
            }

            return value = fn.apply(date);
        }

        public static Lazy<Date> of(Function<Date, Date> fn) {
            return new Lazy<>(fn);
        }
    }

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
        if (evaluatedValue != null) {
            return evaluatedValue;
        }
        return doEval(value);
    }

    protected synchronized GeneralCriterion<Date> doEval(Date value) {
        if (evaluatedValue != null) {
            return evaluatedValue;
        }

        OperatorEnum operatorEnum = OperatorEnum.symbolOf(operator.getSymbol());
        return evaluatedValue = new GeneralCriterion<>(fieldName, operatorEnum.getGeneralOperator(),
                singleValueLazy == null ? null : singleValueLazy.evaluate(value),
                listValueLazy == null ? null : listValueLazy.evaluate(value));
    }
}
