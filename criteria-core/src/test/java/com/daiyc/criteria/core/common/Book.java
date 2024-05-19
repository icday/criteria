package com.daiyc.criteria.core.common;

import com.daiyc.criteria.core.annotations.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @author daiyc
 */
@Data
@Accessors(chain = true)
@Schema(BookSchema.class)
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
