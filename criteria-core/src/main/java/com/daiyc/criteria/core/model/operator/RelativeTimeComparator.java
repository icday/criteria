package com.daiyc.criteria.core.model.operator;

import com.daiyc.criteria.core.enums.TimePrecision;
import com.daiyc.criteria.core.enums.TimeUnit;
import com.daiyc.criteria.core.generic.GenericCriterion;
import com.daiyc.criteria.core.model.Criterion;
import com.daiyc.criteria.core.model.CriterionFactory;
import com.daiyc.criteria.core.model.Operator;
import com.daiyc.criteria.core.model.RelativeTimeCriterion;
import com.daiyc.criteria.core.schema.CriteriaSchema;
import com.daiyc.criteria.core.schema.FieldInfo;
import lombok.Getter;

import java.util.Optional;

/**
 * @author daiyc
 */
public class RelativeTimeComparator extends SingleOperandOperator {
    @Getter
    private final Operator rewriteOperator;

    public RelativeTimeComparator(String symbol, OperatorWrapper rewriteOperator) {
        this(symbol, rewriteOperator.getTarget());
    }

    public RelativeTimeComparator(String symbol, Operator rewriteOperator) {
        super(symbol);
        this.rewriteOperator = rewriteOperator;
    }

    @Override
    @SuppressWarnings("all")
    public <T> Criterion<T> toCriterion(GenericCriterion genericCriterion, CriteriaSchema schema) {
        FieldInfo field = schema.getField(genericCriterion.getName());

        TimeUnit timeUnit = TimeUnit.valueOf(genericCriterion.getTimeUnit());
        TimePrecision timePrecision = Optional.ofNullable(genericCriterion.getTimePrecision())
                .map(s -> TimePrecision.valueOf(s.toUpperCase()))
                .orElse(null);

        Integer num = Integer.valueOf(genericCriterion.getValue().toString());

        return (Criterion<T>) CriterionFactory.relativeTime(genericCriterion.getName(), this
                , num
                , timeUnit
                , timePrecision
        );
    }

    @Override
    public String stringify(Criterion<?> criterion) {
        assert criterion instanceof RelativeTimeCriterion;
        RelativeTimeCriterion relativeTimeCriterion = (RelativeTimeCriterion) criterion;

        Integer delta = relativeTimeCriterion.getDelta();

        return String.format("%s %s #{date(%s)} %s %d(%s)", relativeTimeCriterion.getFieldName(), this.getRewriteOperator().getSymbol(),
                relativeTimeCriterion.getTimePrecision(), delta > 0 ? "+" : "-", delta, relativeTimeCriterion.getTimeUnit());
    }
}
