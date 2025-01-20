package com.test.dux.security.custom;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.dux.dto.ErrorResponse;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.IOException;

public class AuthEntryPointResponse implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setMensaje("Autenticacion no valida: Token JWT invalido o ausente");
        errorResponse.setCodigo(401);
        ObjectMapper objectMapper = new ObjectMapper();

        response.getOutputStream().write(objectMapper.writeValueAsBytes(errorResponse));
    }
}
