package com.daiyc.criteria.core.common;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @author daiyc
 */
@Data
@Accessors(chain = true)
public class Book {
    /**
     * The id of book.
     */
    private Long id;

    /**
     * The name of book.
     */
    private String name;

    /**
     * The category of book.
     */
    private Integer category;

    /**
     * The tags of book.
     */
    private List<String> tags;
}
