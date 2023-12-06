package com.br.readnow.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.br.readnow.api.model.UsuarioModel;
import com.br.readnow.api.service.UsuarioService;

import jakarta.validation.Valid;

import com.br.readnow.api.dto.EmailDTO;
import com.br.readnow.api.dto.LoginDTO;
import com.br.readnow.api.dto.PerfilDTO;
import com.br.readnow.api.dto.RequestNovaSenhaDTO;

@RestController
public class UsuarioController {
    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("/efetua-login")
    public ResponseEntity<?> efetuarLogin(@RequestBody @Valid LoginDTO login){
        return usuarioService.login(login);
    }

    @PostMapping("/efetua-cadastro")
    public ResponseEntity<?> cadastrarUsuario(@RequestBody @Valid UsuarioModel usuarioModel){
        return usuarioService. cadastrarUsuario(usuarioModel);
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

    @PutMapping("/foto")
    public ResponseEntity<?> alterarFoto(@RequestPart(value = "foto") MultipartFile foto,
                                         @RequestPart(value = "nome") String nome,
                                         @RequestParam(value = "email") String email){
        return usuarioService.alterarFoto(foto, nome, email);
    }

    @GetMapping("/busca-foto")
    public ResponseEntity<PerfilDTO> buscarPerfil(@RequestParam(value = "email") String email){
        return usuarioService.retornarNomeEFoto(email);
    }
}