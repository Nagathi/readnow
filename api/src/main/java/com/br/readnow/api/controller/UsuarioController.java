package com.br.readnow.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.br.readnow.api.model.UsuarioModel;
import com.br.readnow.api.service.UsuarioService;
import com.br.readnow.api.dto.LoginDTO;
import com.br.readnow.api.dto.RequestNovaSenhaDTO;
import com.br.readnow.api.dto.UsuarioDTO;

@Controller
public class UsuarioController {
    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/login")
    public String logar() {
        return "login";
    }

    @PostMapping("/logar")
    public ResponseEntity<UsuarioDTO> efetuarLogin(@RequestBody LoginDTO login){
        return usuarioService.login(login);
    }


    @PostMapping("/cadastrar")
    public ResponseEntity<?> cadastrarUsuario(@RequestBody UsuarioModel usuario){
        return usuarioService. cadastrarUsuario(usuario);
    }

    @GetMapping("/enviaEmail")
    public ResponseEntity<?> enviarEmail(@RequestParam(value = "email") String email){
        return usuarioService.enviarEmail(email);
    }

    @GetMapping("/redefineSenha/{token}")
    public ResponseEntity<?> redefinirSenha(@PathVariable String token) {
        return usuarioService.recuperarSenha(token);
    }

    @GetMapping("/alteraSenha")
    public ResponseEntity<?> redefinirSenha(@RequestBody RequestNovaSenhaDTO request) {
        return usuarioService.redefinirSenha(request);
    }

    @GetMapping("/login")
    public String logar() {
        return "login";
    }

    @GetMapping("/cadastro")
    public String cadastrar() {
        return "new_user";
    }

    @GetMapping("/esqueci-minha-senha")
    public String recuperarSenha() {
        return "formularioEmail";
    }
}
