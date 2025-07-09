package com.nestaway.model;

public enum TypeNotif {
    DELETE(1),
    NEW(2),
    MODIFY(3);

    private final int id;

    private TypeNotif(int id) {
        this.id = id;
    }

    public static TypeNotif fromInt(int id) {
        for (TypeNotif type : values()) {
            if (type.getId() == id) {
                return type;
            }
        }
        return null;
    }

    public int getId() {
        return id;
    }
}
