package com.br.readnow.api.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.br.readnow.api.model.AvaliacaoModel;
import com.br.readnow.api.model.LivroModel;
import com.br.readnow.api.model.UsuarioModel;

@Repository
public interface AvaliacaoRepository extends CrudRepository<AvaliacaoModel, Long> {
    AvaliacaoModel findByUsuarioAndLivro(UsuarioModel usuario, LivroModel livro);

    @Query("SELECT COALESCE(AVG(a.qtdEstrelas), 0) FROM AvaliacaoModel a WHERE a.livro.codigo = :codigoLivro")
    double findMediaAvaliacaoByLivroCodigo(Long codigoLivro);
}
