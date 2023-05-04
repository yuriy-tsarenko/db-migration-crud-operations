package com.goit.crud.exception;

public class DatasourceException extends RuntimeException {

    public DatasourceException() {
    }

    public DatasourceException(String message) {
        super(message);
    }

    public DatasourceException(String message, Throwable cause) {
        super(message, cause);
    }
}
