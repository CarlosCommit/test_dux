package com.test.dux.controller;

import com.test.dux.dto.CredentialDTO;
import com.test.dux.exception.NotAuthenticatedUserException;
import com.test.dux.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;
    private final Logger log = LoggerFactory.getLogger(AuthController.class);
    public AuthController(AuthService authService) {
        this.authService = authService;
    }
    @Operation(
            summary = "Obtener token de autenticacion",
            description = "Obtiene un token si las credenciales corresponden a un usuario valido"
    )
    @ApiResponses(value ={
            @ApiResponse(responseCode = "401", description = "Las credenciales son erroneas"),
            @ApiResponse(responseCode = "500", description = "Error interno en el servidor, revisar logs")
    })

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody CredentialDTO credentialDTO) throws NotAuthenticatedUserException {
        log.info("Se recibe peticion para iniciar sesion: {} ",credentialDTO);

        Map<String, String> response = new HashMap<>();
        String token = authService.login(credentialDTO);
        response.put("token", token);

        log.info("Se genera token jwt: {} ",token);
        return ResponseEntity.ok(response);

    }
}
