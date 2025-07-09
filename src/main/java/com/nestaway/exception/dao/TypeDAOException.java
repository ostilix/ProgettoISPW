package com.nestaway.exception.dao;

public enum TypeDAOException {
    GENERIC(1),
    DUPLICATE(2),
    NOT_AVAILABLE(3);

    private final int id;

    private TypeDAOException(int id) {
        this.id = id;
    }

    public static TypeDAOException fromInt(int id){
        for (TypeDAOException type : values()){
            if (type.getId() == id){
                return type;
            }
        }
        return null;
    }

    public int getId(){
        return id;
    }
}