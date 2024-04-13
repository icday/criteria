package com.daiyc.criteria.mybatis;

import com.daiyc.criteria.core.model.Element;
import com.daiyc.criteria.mybatis.annotations.Table;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.builder.annotation.ProviderContext;
import org.apache.ibatis.builder.annotation.ProviderMethodResolver;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * @author daiyc
 */
public class CriteriaSqlProvider implements ProviderMethodResolver {
    private static final List<String> QUERY_LIST_METHOD_PREFIXES = Arrays.asList("query", "select", "find", "get", "page");

    private static final List<String> COUNT_METHOD_PREFIXES = Collections.singletonList("count");

    private static final Method LIST_QUERY_METHOD;

    private static final Method COUNT_QUERY_METHOD;

    static {
        try {
            LIST_QUERY_METHOD = CriteriaSqlProvider.class.getMethod("buildListQuery", Element.class, ProviderContext.class);
            COUNT_QUERY_METHOD = CriteriaSqlProvider.class.getMethod("buildCountQuery", Element.class, ProviderContext.class);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Method resolveMethod(ProviderContext context) {
        Method mapperMethod = context.getMapperMethod();
        Class<?> returnType = mapperMethod.getReturnType();
        if (Number.class.isAssignableFrom(returnType)) {
            return COUNT_QUERY_METHOD;
        }

        if (Collection.class.isAssignableFrom(returnType)) {
            return LIST_QUERY_METHOD;
        }

        String methodName = mapperMethod.getName();
        if (QUERY_LIST_METHOD_PREFIXES.stream()
                .anyMatch(methodName::startsWith)) {
            return LIST_QUERY_METHOD;
        } else if (COUNT_METHOD_PREFIXES.stream().anyMatch(methodName::startsWith)) {
            return COUNT_QUERY_METHOD;
        }
        return ProviderMethodResolver.super.resolveMethod(context);
    }

    public static String buildCountQuery(Element criteria, ProviderContext context) {
        String tableName = readTableName(context);

        String condition = buildCondition(criteria);

        StringBuilder sb = new StringBuilder()
                .append("SELECT COUNT(*) FROM ")
                .append(tableName);
        if (condition != null && !condition.isEmpty()) {
            sb
                    .append(" WHERE ")
                    .append(condition);
        }
        return sb.toString();
    }

    public static String buildListQuery(@Param("criteria") Element criteria, ProviderContext context) {
        String tableName = readTableName(context);

        String condition = buildCondition(criteria);

        StringBuilder sb = new StringBuilder()
                .append("SELECT * FROM ")
                .append(tableName);

        if (condition != null && !condition.isEmpty()) {
            sb
                    .append(" WHERE ")
                    .append(condition);
        }

        return sb.toString();
    }

    protected static String buildCondition(Element criteria) {
        return criteria.transform(new CriteriaSqlTransformer("criteria"));
    }

    protected static String readTableName(ProviderContext context) {
        Class<?> mapperType = context.getMapperType();
        com.daiyc.criteria.mybatis.annotations.Table ann = mapperType.getAnnotation(Table.class);
        return ann.value();
    }
}
