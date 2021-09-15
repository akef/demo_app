package com.demo.jumia.exception;

public class NoDataException extends Exception{
    private static final long serialVersionUID = 1L;

    private String errorMessage;

    public NoDataException() {
        super();
    }

    public NoDataException(String errorMessage) {
        super(errorMessage);
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}

