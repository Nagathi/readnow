package com.br.readnow.api.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.br.readnow.api.model.UsuarioModel;


@Repository
public interface UsuarioRepository extends  CrudRepository <UsuarioModel, Long>{
    Optional<UsuarioModel> findByEmailAndSenha(String email, String senha);
    boolean existsByEmail(String email);
    boolean existsByUsuario(String usuario);
}
