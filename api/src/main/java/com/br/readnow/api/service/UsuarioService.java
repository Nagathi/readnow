package com.br.readnow.api.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.br.readnow.api.model.UsuarioModel;
import com.br.readnow.api.repository.UsuarioRepository;

@Service
public class UsuarioService {
    @Autowired
    private UsuarioRepository usuarioRepository;

    public Optional<UsuarioModel> login(String email, String senha){
        return usuarioRepository.findByEmailAndSenha(email, senha);
    }
}
