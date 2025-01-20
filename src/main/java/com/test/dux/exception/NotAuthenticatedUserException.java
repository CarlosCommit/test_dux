package com.test.dux.exception;

public class NotAuthenticatedUserException extends Exception{
    public NotAuthenticatedUserException(String message) {
        super(message);
    }

    public NotAuthenticatedUserException(String message, Throwable cause) {
        super(message, cause);
    }
}
