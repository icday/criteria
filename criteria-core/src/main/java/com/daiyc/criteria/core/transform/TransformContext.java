package com.daiyc.criteria.core.transform;

import com.daiyc.criteria.core.model.Criteria;
import com.daiyc.criteria.core.model.Condition;
import io.vavr.Tuple;
import io.vavr.Tuple2;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author daiyc
 */
@Data
@Accessors(chain = true)
@RequiredArgsConstructor
public class TransformContext {

    private final List<Tuple2<Criteria, Integer>> tracers;

    private final Condition current;

    public TransformContext(Condition current) {
        this(Collections.emptyList(), current);
    }

    public TransformContext next(int next) {
        assert current instanceof Criteria;
        Criteria criteria = (Criteria) current;

        List<Tuple2<Criteria, Integer>> newTracers = new ArrayList<>(tracers);
        newTracers.add(Tuple.of(criteria, next));

        Condition condition = criteria.getChildren().get(next);
        return new TransformContext(newTracers, condition);
    }

    public boolean isRoot() {
        return tracers.isEmpty();
    }

    public Criteria getParent() {
        if (tracers.isEmpty()) {
            return null;
        }
        return tracers.get(tracers.size() - 1)._1;
    }
}
