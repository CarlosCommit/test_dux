package com.test.dux.controller;

import com.test.dux.dto.CredentialDTO;
import com.test.dux.dto.ErrorResponse;
import com.test.dux.exception.NotAuthenticatedUserException;
import com.test.dux.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
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
            @ApiResponse(responseCode = "200", description = "Las credenciales son erroneas",
                    content = @Content(mediaType = "application/json",
                    examples = @ExampleObject(value = "{\"token\": \"eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0Iiwicm9sZSI6IltBRE1JTl0iLCJleHAiOjE3Mzc0MDUxNDcsImlhdCI6MTczNzQwMTU0N30.WHNCCxH6J1eNptPmZn_H4vCRaD0SdZf3JVcMXRR5Z0E\"}"))),

            @ApiResponse(responseCode = "401", description = "Las credenciales son erroneas",   content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ErrorResponse.class),
                    examples = @ExampleObject(value = "{\"mensaje\": \"Credenciales incorrectas\", \"codigo\": \"401\"}")
            )),
            @ApiResponse(responseCode = "500",
                    description = "Error interno en el servidor, revisar logs",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = @ExampleObject(value = "{\"mensaje\": \"Ocurrio un error inesperado\", \"codigo\": \"500\"}")
                    )
            )
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
