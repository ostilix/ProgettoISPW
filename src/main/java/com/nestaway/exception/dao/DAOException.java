package com.nestaway.exception.dao;

public class DAOException extends Exception{

    final TypeDAOException typeException;

    public DAOException(TypeDAOException typeException) {
        super();
        this.typeException = typeException;
    }

    public DAOException(String message, Throwable cause, TypeDAOException typeException) {
        super(message, cause);
        this.typeException = typeException;
    }

    public DAOException(String message, TypeDAOException typeException) {
        super(message);
        this.typeException = typeException;
    }

    public TypeDAOException getTypeException() {
        return typeException;
    }
}
