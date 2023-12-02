package com.br.readnow.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.br.readnow.api.model.EnderecoModel;

@Repository
public interface EnderecoRepository extends CrudRepository<EnderecoModel, Long> {

    @Query("SELECT e FROM EnderecoModel e WHERE e.usuario.id = :usuarioId")
    List<EnderecoModel> findAllByUsuarioId(Long usuarioId);
}
