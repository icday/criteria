package com.daiyc.criteria.core.transform;

import com.daiyc.criteria.core.model.Combinator;
import com.daiyc.criteria.core.model.Criteria;
import com.daiyc.criteria.core.model.Criterion;
import com.daiyc.criteria.core.model.Element;
import io.vavr.collection.Stream;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.daiyc.criteria.core.model.Combinator.NOT;

/**
 * @author daiyc
 */
public class Simplify implements Transformer<Element> {
    @Override
    public Element transform(Criteria criteria, Element newValue, TransformContext ctx) {
        return newValue;
    }

    @Override
    public Element transform(Criterion<?> criterion, TransformContext ctx) {
        return criterion;
    }

    @Override
    public Element combine(Combinator combinator, Collection<Element> list) {
        if (list == null || list.isEmpty()) {
            return null;
        }

        List<Element> elements = list.stream().filter(Objects::nonNull).collect(Collectors.toList());

        if (combinator == NOT) {
            assert elements.size() == 1;
            Element element = list.iterator().next();
            if (element instanceof Criteria) {
                Criteria c = (Criteria) element;
                if (c.getCombinator() == NOT) {
                    // 双重否定 = 肯定
                    return c.getChildren().get(0);
                }
            }
            return Criteria.not(element);
        }

        List<Element> newElements = Stream.ofAll(elements)
                .partition(e -> e instanceof Criteria && ((Criteria) e).getCombinator() == combinator)
                .apply((s, s2) -> {
                    List<Criteria> cs = s.map(e -> (Criteria) e).toJavaList();
                    return merge(cs, s2.toJavaList());
                });

        if (newElements.isEmpty()) {
            return null;
        }

        if (newElements.size() == 1) {
            return newElements.get(0);
        }

        return Criteria.newCriteria(combinator, newElements);
    }

    protected List<Element> merge(List<Criteria> list, List<Element> others) {
        return Stream.ofAll(list)
                .flatMap(Criteria::getChildren)
                .appendAll(others)
                .filter(Objects::nonNull)
                .toJavaList();
    }
}
