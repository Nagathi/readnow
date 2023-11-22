package com.br.readnow.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.br.readnow.api.model.PedidoModel;
import com.br.readnow.api.service.PedidoService;

@RestController
public class PedidoController {
    @Autowired
    private PedidoService pedidoService;

    @GetMapping("/pedidos")
    public Iterable<PedidoModel> listarLivros(){
        return pedidoService.listarPedidos();
    }

    @PostMapping("/cadastroPedido")
    public ResponseEntity<?> cadastrarPedido(@RequestBody PedidoModel pedidoModel){
        return pedidoService.cadastrarPedido(pedidoModel);
    }


}
