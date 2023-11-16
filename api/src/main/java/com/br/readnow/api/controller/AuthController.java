package com.br.readnow.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.br.readnow.api.dto.UsuarioDTO;
import com.br.readnow.api.service.AuthService;

@RestController
public class AuthController {

    @Autowired
    private AuthService authService;

    @GetMapping("/autenticacao")
    public ResponseEntity<UsuarioDTO> autenticarPorUUID(@RequestParam(value = "id") String id){
        return authService.verificarUUID(id);
    }

}
