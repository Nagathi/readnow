package com.br.readnow.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.br.readnow.api.model.UsuarioModel;
import com.br.readnow.api.service.UsuarioService;
import com.br.readnow.api.dto.EmailDTO;
import com.br.readnow.api.dto.LoginDTO;
import com.br.readnow.api.dto.RequestNovaSenhaDTO;
import com.br.readnow.api.dto.UsuarioDTO;

@RestController
public class UsuarioController {
    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("/efetua-login")
    public ResponseEntity<UsuarioDTO> efetuarLogin(@RequestBody LoginDTO login){
        return usuarioService.login(login);
    }


    @PostMapping("/efetua-cadastro")
    public ResponseEntity<?> cadastrarUsuario(@RequestBody UsuarioModel usuario){
        return usuarioService. cadastrarUsuario(usuario);
    }

    @PostMapping("/envia-email")
    public ResponseEntity<?> enviarEmail(@RequestBody EmailDTO email){
        return usuarioService.enviarEmail(email.getEmail());
    }

    @GetMapping("/redefine-senha/{token}")
    public ResponseEntity<?> redefinirSenha(@PathVariable String token) {
        return usuarioService.recuperarSenha(token);
    }

    @PutMapping("/altera-senha")
    public ResponseEntity<?> redefinirSenha(@RequestBody RequestNovaSenhaDTO request) {
        return usuarioService.redefinirSenha(request);
    }
}