package com.br.readnow.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.br.readnow.api.model.CartaoModel;


@Repository
public interface CartaoRepository extends CrudRepository<CartaoModel, Long>{
    @Query("SELECT e FROM CartaoModel e WHERE e.usuario.id = :usuarioId")
    List<CartaoModel> findAllByUsuarioId(Long usuarioId);
}
