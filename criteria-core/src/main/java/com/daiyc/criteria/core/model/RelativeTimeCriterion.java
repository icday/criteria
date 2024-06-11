package com.daiyc.criteria.core.model;

import com.daiyc.criteria.core.enums.TimePrecision;
import com.daiyc.criteria.core.enums.TimeUnit;
import com.daiyc.criteria.core.model.operator.RelativeTimeComparator;
import com.daiyc.criteria.core.transform.TransformContext;
import com.daiyc.criteria.core.transform.Transformer;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.Date;
import java.util.List;

/**
 * @author daiyc
 */
@Data
@RequiredArgsConstructor
public class RelativeTimeCriterion implements Criterion<Date> {
    private final String fieldName;

    private final RelativeTimeComparator operator;

    private final Integer delta;

    private final TimeUnit timeUnit;

    private final TimePrecision timePrecision;

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

    @Override
    public Condition evaluate(EvaluateContext ctx) {
        return doEval(ctx.getTime());
    }

    protected GeneralCriterion<Date> doEval(Date value) {
        value = TimePrecision.apply(timePrecision, value);
        value = TimeUnit.apply(timeUnit, value, delta);

        return new GeneralCriterion<>(fieldName, operator.getRewriteOperator(), value);
    }
}
