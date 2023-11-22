package com.br.readnow.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.br.readnow.api.model.PedidoModel;
import com.br.readnow.api.repository.PedidoRepository;

@Service
public class PedidoService {
    @Autowired
    private PedidoRepository pedidoRepository;

    public Iterable<PedidoModel> listarPedidos() {
        return pedidoRepository.findAll();
    }

    public ResponseEntity<?> cadastrarPedido(PedidoModel pedidoModel) {
        PedidoModel novoPedido = new PedidoModel();
        try {
            novoPedido.setCodigoCarrinho(pedidoModel.getCodigoCarrinho());
            novoPedido.setCodigoCliente(pedidoModel.getCodigoCliente());
            novoPedido.setCodigoFormaPagamento(pedidoModel.getCodigoFormaPagamento());
            novoPedido.setDataEntregaPrevista(pedidoModel.getDataEntregaPrevista());
            novoPedido.setDataPedido(pedidoModel.getDataPedido());
            novoPedido.setValorTotal(pedidoModel.getValorTotal());
        } catch (Exception e) {

        }
        if(pedidoRepository.existsByCodigo(novoPedido.getCodigo())){
            return ResponseEntity.badRequest().body("Esse pedido já existe em nosso banco de dados!");

        }
        else{
            pedidoRepository.save(novoPedido);

        }
        return ResponseEntity.status(HttpStatus.CREATED).body("Pedido cadastrado!");

    }

}
