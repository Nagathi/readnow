package com.br.readnow.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.br.readnow.api.model.UsuarioModel;
import com.br.readnow.api.service.UsuarioService;
import com.br.readnow.api.dto.LoginDTO;
import com.br.readnow.api.dto.UsuarioDTO;

@Controller
public class UsuarioController {
    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/login")
    public String logar() {
        return "login";
    }

    @PostMapping("/login")
    public ResponseEntity<UsuarioDTO> efetuarLogin(@RequestBody LoginDTO login){
        return usuarioService.login(login);
    }

    @GetMapping("/cadastro")
    public String cadastrar() {
        return "new_user";
    }

    
    @PostMapping("/cadastro")
    public ResponseEntity<?> cadastrarUsuario(@RequestBody UsuarioModel usuario){
        return usuarioService. cadastrarUsuario(usuario);
    }
}
