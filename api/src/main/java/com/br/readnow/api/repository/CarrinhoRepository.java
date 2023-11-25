package com.br.readnow.api.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.br.readnow.api.model.CarrinhoModel;

@Repository
public interface CarrinhoRepository  extends CrudRepository<CarrinhoModel, Long>{
    boolean existsByCodigo(Long codigo);
}
