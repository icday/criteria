package com.daiyc.criteria.core.schema;

import com.daiyc.criteria.core.enums.DataType;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * @author daiyc
 */
public class SchemaBuilder extends BaseBuilder<SchemaBuilder> {
    private final List<FieldInfo> fields = new ArrayList<>();

    public SchemaBuilder field(Consumer<FieldBuilder> consumer) {
        FieldBuilder fb = new FieldBuilder();
        consumer.accept(fb);
        this.fields.add(fb.build());
        return this;
    }

    public SchemaBuilder field(String name, Consumer<FieldBuilder> consumer) {
        return field(fb -> {
            fb.name(name);
            consumer.accept(fb);
        });
    }

    public SchemaBuilder field(String name, DataType type, Consumer<FieldBuilder> consumer) {
        return field(fb -> {
            fb.name(name);
            fb.type(type);
            consumer.accept(fb);
        });
    }

    public SchemaBuilder field(String name, DataType type) {
        return field(fb -> {
            fb.name(name);
            fb.type(type);
        });
    }

    public SchemaBuilder field(FieldInfo fi) {
        this.fields.add(fi);
        return this;
    }

    public SchemaBuilder field(FieldBuilder fb) {
        this.fields.add(fb.build());
        return this;
    }

    public CriteriaSchema build() {
        return new CriteriaSchema(fields, attributes);
    }

    public static class FieldBuilder extends BaseBuilder<FieldBuilder> {
        private String name;

        private DataType type;

        public FieldBuilder name(String name) {
            this.name = name;
            return this;
        }

        public FieldBuilder type(DataType type) {
            this.type = type;
            return this;
        }


        public FieldInfo build() {
            return new FieldInfo(name, type, attributes);
        }
    }
}
