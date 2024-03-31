package com.daiyc.criteria.mybatis.operator;

import com.daiyc.criteria.core.model.Criterion;

import java.util.ArrayList;
import java.util.List;

/**
 * @author daiyc
 */
public class InTransformer implements OperatorTransformer {
    @Override
    public String transform(String path, Criterion<?> criterion) {
        List<String> args = new ArrayList<>();
        for (int i = 0; i < criterion.getListValue().size(); i++) {
            args.add(String.format("#{%s.listValue[%d]}", path, i));
        }
        return String.format("`%s` IN (%s)", path, String.join(", ", args));
    }
}
