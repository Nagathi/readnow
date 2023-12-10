package com.br.readnow.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.br.readnow.api.model.LivroPedidoModel;
@Repository
public interface LivroPedidoRepository extends CrudRepository<LivroPedidoModel, Long>{
    boolean existsByCodigo(Long codigo);

    @Query("SELECT l FROM LivroItemModel l WHERE l.pedido.codigo = :codigoDoPedido")
    List<LivroPedidoModel> findAllByPedidoCodigo(@Param("codigoDoPedido") Long codigoDoPedido);

}