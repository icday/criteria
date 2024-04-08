package com.daiyc.criteria.core.builder;

import com.daiyc.criteria.core.model.Element;

/**
 * @author daiyc
 */
public interface Builder<T> {
    /**
     * 构建 Criteria
     *
     * @return 条件 或 null
     */
//    Criteria toCriteria();

    Element toCriteria();
}
