package com.br.readnow.api.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.br.readnow.api.model.LivroItemModel;
@Repository
public interface LivroItemRepository extends CrudRepository<LivroItemModel, Long>{
    boolean existsByCodigo(Long codigo);
    List<LivroItemModel> findAllByLivroCodigo(Long codigo);
    Optional<LivroItemModel> findByLivroCodigo(Long codigo);
    List<LivroItemModel> findAllByCarrinhoCodigo(Long codigo);

}
