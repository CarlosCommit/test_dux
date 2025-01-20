package com.test.dux.service;

import com.test.dux.dto.CredentialDTO;
import com.test.dux.exception.NotAuthenticatedUserException;

public interface AuthService {
     String login(CredentialDTO credentialDTO) throws NotAuthenticatedUserException;
}
