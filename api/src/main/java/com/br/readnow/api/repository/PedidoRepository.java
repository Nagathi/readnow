package com.br.readnow.api.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.br.readnow.api.model.PedidoModel;


@Repository
public interface PedidoRepository extends CrudRepository <PedidoModel, Long>{
    boolean existsByCodigo(Long codigo);
}

