package com.test.dux.exception;

public class TeamNotFoundException extends Exception{
    public TeamNotFoundException() {
        super("Equipo no encontrado");
    }

    public TeamNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
