package com.daiyc.criteria.mybatis;

import com.daiyc.criteria.common.Pagination;
import com.daiyc.criteria.core.facade.ConditionMapper;
import com.daiyc.criteria.core.model.Condition;
import com.daiyc.criteria.core.schema.CriteriaSchema;
import com.daiyc.criteria.mybatis.annotations.CriteriaMapper;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.builder.annotation.ProviderContext;
import org.apache.ibatis.builder.annotation.ProviderMethodResolver;

import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author daiyc
 */
public class CriteriaSqlProvider implements ProviderMethodResolver {
    private static final List<String> QUERY_LIST_METHOD_PREFIXES = Arrays.asList("query", "list", "select", "find", "get", "page");

    private static final List<String> COUNT_METHOD_PREFIXES = Collections.singletonList("count");

    private static final Map<Integer, Method> LIST_QUERY_METHODS = new HashMap<>();

    private static final Method COUNT_QUERY_METHOD;

    @RequiredArgsConstructor
    enum ParamType {
        CONDITION(Condition.class),
        PAGINATION(Pagination.class),
        ORDER_BY(String.class),
        ;

        @Getter
        private final Class<?> type;
    }

    static {
        try {
            COUNT_QUERY_METHOD = CriteriaSqlProvider.class.getMethod("buildCountQuery", Condition.class, ProviderContext.class);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }

//        register(Arrays.asList(ParamType.CONDITION, ParamType.PAGINATION, ParamType.ORDER_BY));
//
//        register(Arrays.asList(ParamType.CONDITION, ParamType.PAGINATION));
//
//        register(Arrays.asList(ParamType.CONDITION, ParamType.ORDER_BY));
//
//        register(Collections.singletonList(ParamType.CONDITION));
    }

    @Override
    public Method resolveMethod(ProviderContext context) {
        Method mapperMethod = context.getMapperMethod();
        Class<?> returnType = mapperMethod.getReturnType();
        if (Number.class.isAssignableFrom(returnType)) {
            return COUNT_QUERY_METHOD;
        }

        String methodName = mapperMethod.getName();
        if (Collection.class.isAssignableFrom(returnType) &&
                QUERY_LIST_METHOD_PREFIXES.stream().anyMatch(methodName::startsWith)) {
            return resolveListMethod(mapperMethod);
        } else if (COUNT_METHOD_PREFIXES.stream().anyMatch(methodName::startsWith)) {
            return COUNT_QUERY_METHOD;
        }
        return ProviderMethodResolver.super.resolveMethod(context);
    }

    @SneakyThrows
    private static Method resolveListMethod(Method mapperMethod) {
        Class<?>[] parameterTypes = mapperMethod.getParameterTypes();
        List<ParamType> types = Arrays.stream(parameterTypes)
                .map(type -> {
                    if (Condition.class.isAssignableFrom(type)) {
                        return ParamType.CONDITION;
                    } else if (Pagination.class.isAssignableFrom(type)) {
                        return ParamType.PAGINATION;
                    } else if (String.class.isAssignableFrom(type)) {
                        return ParamType.ORDER_BY;
                    }
                    return null;
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        List<Class<?>> list = types.stream()
                .distinct()
                .sorted()
                .map(t -> (Class<?>) t.getType())
                .collect(Collectors.toList());
        list.add(ProviderContext.class);
        Class[] classes = list.toArray(new Class[0]);

        return CriteriaSqlProvider.class.getMethod("buildListQuery", classes);
    }

    public static String buildCountQuery(@Param("criteria") Condition criteria, ProviderContext context) {
        String tableName = readTableName(context);
        CriteriaSchema schema = readSchema(context);

        return "SELECT COUNT(*) FROM " +
                tableName +
                buildListSqlPart(criteria, schema);
    }

    public static String buildListQuery(@Param("criteria") Condition criteria, ProviderContext context) {
        return buildListQuery(criteria, null, null, context);
    }

    public static String buildListQuery(
            @Param("criteria") Condition criteria,
            @Param("orderBy") String orderBy,
            ProviderContext context) {
        return buildListQuery(criteria, null, orderBy, context);
    }

    public static String buildListQuery(
            @Param("criteria") Condition criteria,
            @Param("pagination") Pagination pagination,
            ProviderContext context) {
        return buildListQuery(criteria, pagination, null, context);
    }

    public static String buildListQuery(
            @Param("criteria") Condition criteria,
            @Param("pagination") Pagination pagination,
            @Param("orderBy") String orderBy,
            ProviderContext context) {
        String tableName = readTableName(context);
        CriteriaSchema schema = readSchema(context);

        return "SELECT * FROM " +
                tableName +
                buildListSqlPart(criteria, pagination, orderBy, schema);
    }

    private static String buildListSqlPart(Condition condition, CriteriaSchema schema) {
        return buildListSqlPart(condition, null, null, schema);
    }

    private static String buildListSqlPart(Condition condition, Pagination pagination, String orderBy, CriteriaSchema schema) {
        StringBuilder sb = new StringBuilder();

        String where = buildCondition(condition, schema);

        if (StringUtils.isNotBlank(where)) {
            sb.append(" WHERE ");
            sb.append(where);
        }

        if (StringUtils.isNotBlank(orderBy)) {
            sb.append(" ORDER BY ");
            sb.append(orderBy);
        }

        if (pagination != null) {
            sb.append(" LIMIT ");
            sb.append("#{pagination.offset}");
            sb.append(", ");
            sb.append("#{pagination.limit}");
        }
        return sb.toString();
    }


    protected static String buildCondition(Condition criteria, CriteriaSchema schema) {
        return criteria.transform(new CriteriaSqlTransformer("criteria"), schema);
    }

    protected static String readTableName(ProviderContext context) {
        Class<?> mapperType = context.getMapperType();
        CriteriaMapper ann = mapperType.getAnnotation(CriteriaMapper.class);
        return ann.tableName();
    }

    protected static CriteriaSchema readSchema(ProviderContext context) {
        Class<?> mapperType = context.getMapperType();
        CriteriaMapper ann = mapperType.getAnnotation(CriteriaMapper.class);
        Class<?> schemaClass = ann.schemaClass();
        return ConditionMapper.DEFAULT_MAPPER.getSchema(schemaClass);
    }
}
