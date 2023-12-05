package com.br.readnow.api.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.br.readnow.api.model.LivroModel;

@Repository
public interface LivroRepository extends CrudRepository <LivroModel, Long>{
    boolean existsByIsbn(String isbn);
    List<LivroModel> findByCategoria(String categoria);
    List<LivroModel> findByTituloContainingOrAutorContainingOrEditoraContainingOrIsbnContainingOrCategoriaContaining(
            String titulo, String autor, String editora, String isbn, String categoria);
}
