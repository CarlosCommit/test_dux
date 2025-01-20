package com.test.dux.security.custom;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.dux.dto.ErrorResponse;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import java.io.IOException;

public class AccessDeniedResponse implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);

        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setMensaje("Autorizacion no valida: debe presentar los permisos necesarios");
        errorResponse.setCodigo(403);
        ObjectMapper objectMapper = new ObjectMapper();

        response.getOutputStream().write(objectMapper.writeValueAsBytes(errorResponse));
    }
}
