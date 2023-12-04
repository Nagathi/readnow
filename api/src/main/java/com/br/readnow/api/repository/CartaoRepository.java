package com.br.readnow.api.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.br.readnow.api.model.CartaoModel;

@Repository
public interface CartaoRepository extends CrudRepository<CartaoModel, Long>{
    
}
