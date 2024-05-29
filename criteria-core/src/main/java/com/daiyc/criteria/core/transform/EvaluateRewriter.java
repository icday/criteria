package com.daiyc.criteria.core.transform;

import com.daiyc.criteria.core.model.Condition;
import com.daiyc.criteria.core.model.Criterion;
import com.daiyc.criteria.core.model.RelativeTimeCriterion;
import lombok.RequiredArgsConstructor;

import java.util.Date;

/**
 * @author daiyc
 */
@RequiredArgsConstructor
public class EvaluateRewriter implements Rewriter {
    private final Date datetime;

    @Override
    public Condition rewrite(Criterion<?> criterion) {
        if (!(criterion instanceof RelativeTimeCriterion)) {
            return criterion;
        }

        RelativeTimeCriterion relativeTimeCriterion = (RelativeTimeCriterion) criterion;
        return relativeTimeCriterion.evaluate(datetime);
    }
}
