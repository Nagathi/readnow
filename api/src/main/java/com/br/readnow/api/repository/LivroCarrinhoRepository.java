package com.br.readnow.api.repository;

import org.springframework.data.repository.CrudRepository;

import com.br.readnow.api.model.LivroCarrinhoModel;

public interface LivroCarrinhoRepository extends CrudRepository<LivroCarrinhoModel, Long>{
    boolean existsByCodigo(Long codigo);

}
