package com.br.readnow.api.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.br.readnow.api.model.UsuarioModel;

public interface UsuarioRepository extends  CrudRepository <UsuarioModel, Long>{
    Optional<UsuarioModel> findByEmailAndSenha(String email, String senha);
}
