package com.test.dux.controller;

import com.test.dux.dto.ErrorResponse;
import com.test.dux.exception.NotAuthenticatedUserException;
import com.test.dux.exception.TeamNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;

import org.springframework.web.bind.MethodArgumentNotValidException;
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
    @ExceptionHandler(TeamNotFoundException.class)
    public ResponseEntity<ErrorResponse> handlerTeamNotFoundException(TeamNotFoundException ex) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setCodigo(404);
        errorResponse.setMensaje(ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handlerMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        String errors = ex.getFieldErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).toList().toString();
        log.info("Error de validacion: {}", errors);

        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setCodigo(400);
        errorResponse.setMensaje("La solicitud es invalida");
        return ResponseEntity.badRequest().body(errorResponse);
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handlerException(Exception ex) {
        log.error("error: {}",ex.getMessage(),ex);
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setCodigo(500);
        errorResponse.setMensaje("Ocurrio un error inesperado");
        return ResponseEntity.internalServerError().body(errorResponse);
    }
}
