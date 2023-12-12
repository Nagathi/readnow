package com.br.readnow.api.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.br.readnow.api.model.AuthModel;
import com.br.readnow.api.repository.TokenRepository;

@RestController
public class TokenController {

    @Autowired
    private TokenRepository authRepository;

    @PostMapping("/encerra-sessao")
    public boolean encerrarSessao(@RequestHeader("Authorization") String authorization) {
        String token = authorization.replace("Bearer ", "");
        Optional<AuthModel> authModel = authRepository.findById(token);
        if (authModel.get().isExpirado()) {
            return true;
        }
        return false;
    }
}
