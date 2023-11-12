package com.br.readnow.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.br.readnow.api.service.UsuarioService;

@RestController
public class UsuarioController {
    @Autowired
    private UsuarioService usuarioService;

    
}
