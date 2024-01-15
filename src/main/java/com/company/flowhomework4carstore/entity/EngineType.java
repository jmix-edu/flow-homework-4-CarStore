package com.company.flowhomework4carstore.entity;

import io.jmix.core.metamodel.datatype.EnumClass;

import javax.annotation.Nullable;


public enum EngineType implements EnumClass<String> {

    GASOLINE("G"),
    ELECTRIC("E");

    private String id;

    EngineType(String value) {
        this.id = value;
    }

    public String getId() {
        return id;
    }

    @Nullable
    public static EngineType fromId(String id) {
        for (EngineType at : EngineType.values()) {
            if (at.getId().equals(id)) {
                return at;
            }
        }
        return null;
    }
}