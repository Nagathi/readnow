package com.br.readnow.api.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.br.readnow.api.model.PedidoModel;
import com.br.readnow.api.model.UsuarioModel;


@Repository
public interface PedidoRepository extends CrudRepository <PedidoModel, Long>{
    boolean existsByCodigo(Long codigo);
    List<PedidoModel> findByUsuarioAndEntregueTrue(UsuarioModel usuario);
    List<PedidoModel> findByUsuarioAndEntregueFalse(UsuarioModel usuario);
    List<PedidoModel> findAllByUsuario(UsuarioModel usuario);
    boolean existsByCodigoAndUsuario(Long codigo, UsuarioModel usuario);

}

