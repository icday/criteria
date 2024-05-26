package com.daiyc.criteria.core.transform;

import com.daiyc.criteria.core.model.Condition;
import com.daiyc.criteria.core.model.Criteria;
import com.daiyc.criteria.core.model.Criterion;
import com.daiyc.criteria.core.model.RelativeTimeCriterion;
import lombok.RequiredArgsConstructor;

import java.util.Date;

/**
 * @author daiyc
 */
@RequiredArgsConstructor
public class EvaluateRewriter extends BaseTransformer<Condition> {
    private final Date datetime;

    @Override
    public Condition transform(Criteria criteria, Condition newValue, TransformContext ctx) {
        return newValue;
    }

    @Override
    public Condition transform(Criterion<?> criterion, TransformContext ctx) {
        if (!(criterion instanceof RelativeTimeCriterion)) {
            return criterion;
        }

        RelativeTimeCriterion relativeTimeCriterion = (RelativeTimeCriterion) criterion;
        return relativeTimeCriterion.evaluate(datetime);
    }
}
