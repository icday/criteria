package com.daiyc.criteria.core.transform;

import com.daiyc.criteria.core.model.Combinator;

import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * @author daiyc
 */
public abstract class BaseLazyTransformer<U> implements Transformer<U> {
    @Override
    public U lazyCombine(Combinator combinator, List<Supplier<U>> list) {
        if (list == null || list.isEmpty()) {
            return null;
        }

        if (combinator == Combinator.NOT) {
            List<Supplier<U>> conditions = list.stream().filter(Objects::nonNull).collect(Collectors.toList());
            assert conditions.size() == 1;
            return not(conditions.get(0));
        }

        return doCombine(combinator, list);
    }

    protected abstract U not(Supplier<U> value);

    protected abstract U doCombine(Combinator combinator, List<Supplier<U>> list);
}
