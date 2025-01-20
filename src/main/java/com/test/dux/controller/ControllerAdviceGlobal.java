package com.test.dux.controller;

import com.test.dux.dto.ErrorResponse;
import com.test.dux.exception.NotAuthenticatedUserException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ControllerAdviceGlobal {
    private final Logger log = LoggerFactory.getLogger(ControllerAdviceGlobal.class);
    @ExceptionHandler({BadCredentialsException.class, NotAuthenticatedUserException.class})
    public ResponseEntity<ErrorResponse> handlerExceptionBadCredentials(Exception ex) {
        log.error("error: {}",ex.getMessage(),ex);
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setCodigo(401);
        errorResponse.setMensaje("Credenciales incorrectas");
        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
    }
}
