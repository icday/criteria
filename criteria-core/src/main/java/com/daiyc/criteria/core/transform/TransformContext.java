package com.daiyc.criteria.core.transform;

import com.daiyc.criteria.core.model.Criteria;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author daiyc
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class TransformContext {
    /**
     * 父级 Criteria
     */
    private Criteria parent;

    /**
     * 到子级的 path
     */
    private String path = "";

    public TransformContext(Criteria parent, int cid) {
        this.parent = parent;
        path = String.format(".children[%d]", cid);
    }

    public TransformContext turnTo(Criteria next, int idx) {
        String p = path == null ? "" : path;

        p = p + ".children[" + idx + "]";

        return new TransformContext()
                .setParent(next)
                .setPath(p);
    }
}
