package com.br.readnow.api.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.auth0.jwt.exceptions.TokenExpiredException;
import com.br.readnow.api.model.AuthModel;
import com.br.readnow.api.repository.TokenRepository;

@RestController
public class TokenController {

    @Autowired
    private TokenRepository authRepository;

    @PostMapping("/encerra-sessao")
    public ResponseEntity<?> encerrarSessao(@RequestHeader("Authorization") String authorization) {
        String token = authorization.replace("Bearer ", "");
        try {
            Optional<AuthModel> authModel = authRepository.findById(token);
            if (authModel.isPresent() && authModel.get().isExpirado()) {
                throw new TokenExpiredException(token, null);
            } else {
                return ResponseEntity.status(200).build();

            }

        } catch (TokenExpiredException e) {
            return ResponseEntity.status(500).build();

        }
    }
}