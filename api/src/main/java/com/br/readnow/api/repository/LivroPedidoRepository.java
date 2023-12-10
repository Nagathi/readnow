package com.br.readnow.api.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.br.readnow.api.model.LivroPedidoModel;
@Repository
public interface LivroPedidoRepository extends CrudRepository<LivroPedidoModel, Long>{

    @Query("SELECT CASE WHEN COUNT(l) > 0 THEN true ELSE false END FROM LivroPedidoModel l WHERE l.livro.codigo = :codigoLivro AND l.usuario.codigo = :codigoUsuario")
    boolean existsByLivroAndUsuario(Long codigoLivro, Long codigoUsuario);

}