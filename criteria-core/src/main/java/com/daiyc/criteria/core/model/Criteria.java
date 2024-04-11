package com.daiyc.criteria.core.model;

import com.daiyc.criteria.core.transform.Rewriter;
import com.daiyc.criteria.core.transform.Stringify;
import com.daiyc.criteria.core.transform.TransformContext;
import com.daiyc.criteria.core.transform.Transformer;
import lombok.Data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author daiyc
 */
@Data
public class Criteria implements Element {
    private final Combinator combinator;

    private final List<Element> children;

    Criteria(Combinator combinator, List<Element> children) {
        assert combinator != Combinator.NOT || children.size() == 1;

        this.combinator = combinator;
        this.children = children;
    }

    public static Element not(Element element) {
        if (element == null) {
            return null;
        }

        return newCriteria(Combinator.NOT, Collections.singletonList(element));
    }

    public static Element or(List<? extends Element> elements) {
        return newCriteria(Combinator.OR, new ArrayList<>(elements));
    }

    public static Element and(List<? extends Element> elements) {
        return newCriteria(Combinator.AND, new ArrayList<>(elements));
    }

    public static Element newCriteria(final Combinator combinator, List<Element> elements) {
        if (elements == null || elements.isEmpty()) {
            return null;
        }

        if (elements.size() == 1 && combinator != Combinator.NOT) {
            return elements.get(0);
        }

        return new Criteria(combinator, elements);
    }

    @Override
    public <T> T transform(Transformer<T> transformer, TransformContext ctx) {
        List<T> tList = new ArrayList<>();
        for (int i = 0; i < children.size(); i++) {
            Element element = children.get(i);
            TransformContext newCtx = ctx.next(i);
            T t;
            if (element instanceof Criteria) {
                Criteria subCriteria = (Criteria) element;
                T subValue = subCriteria.transform(transformer, newCtx);
                t = transformer.transform(subCriteria, subValue, newCtx);
            } else {
                Criterion<?> criterion = (Criterion<?>) element;
                t = transformer.transform(criterion, newCtx);
            }
            tList.add(t);
        }

        return transformer.combine(combinator, tList);
    }

    @Override
    public Element accept(Rewriter rewriter) {
        List<Element> elements = children.stream()
                .map(e -> e.accept(rewriter))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        return new Criteria(combinator, elements);
    }

    @Override
    public String toString() {
        return this.transform(new Stringify());
    }
}
