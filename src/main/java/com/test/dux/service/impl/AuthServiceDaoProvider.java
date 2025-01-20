package com.test.dux.service.impl;

import com.test.dux.dto.CredentialDTO;
import com.test.dux.exception.NotAuthenticatedUserException;
import com.test.dux.security.jwt.JwtUtils;
import com.test.dux.service.AuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceDaoProvider implements AuthService {
    private final Logger log = LoggerFactory.getLogger(AuthServiceDaoProvider.class);
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;

    public AuthServiceDaoProvider(AuthenticationManager authenticationManager, JwtUtils jwtUtils) {
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
    }

    public String login(CredentialDTO credentialDTO) throws NotAuthenticatedUserException {
        log.info("Inicia autenticacion mediante DaoProvider");
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(credentialDTO.getUsername(), credentialDTO.getPassword());
        Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        if(authentication.isAuthenticated())
        {
            log.info("Usuario autenticado correctamente");
            return jwtUtils.generateToken(credentialDTO.getUsername(),authentication.getAuthorities().toString());
        }

        throw new NotAuthenticatedUserException("El usuario no esta autenticado");

    }
}
