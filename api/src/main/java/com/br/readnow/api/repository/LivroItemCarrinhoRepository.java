package com.br.readnow.api.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.br.readnow.api.model.LivroItemCarrinhoModel;
@Repository
public interface LivroItemCarrinhoRepository extends CrudRepository<LivroItemCarrinhoModel, Long>{
    boolean existsByCodigo(Long codigo);
    List<LivroItemCarrinhoModel> findAllByLivroCodigo(Long codigo);
    Optional<LivroItemCarrinhoModel> findByLivroCodigo(Long codigo);
    List<LivroItemCarrinhoModel> findAllByCarrinhoCodigo(Long codigo);

}
