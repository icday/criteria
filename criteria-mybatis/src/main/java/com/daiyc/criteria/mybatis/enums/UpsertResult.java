package com.daiyc.criteria.mybatis.enums;

/**
 * Upsert 操作的结果
 *
 * @author daiyc
 */
public class UpsertResult {

    /**
     * 无变化
     */
    public static final int NO_CHANGE = 0;

    /**
     * 插入
     */
    public static final int INSERTED = 1;

    /**
     * 更新
     */
    public static final int UPDATED = 2;
}
