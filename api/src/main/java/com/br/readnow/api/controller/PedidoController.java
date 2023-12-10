package com.br.readnow.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.br.readnow.api.dto.PedidoDTO;
import com.br.readnow.api.dto.PedidoEntregueDTO;
import com.br.readnow.api.dto.PedidoPendenteDTO;
import com.br.readnow.api.service.PedidoService;

@RestController
public class PedidoController {
    @Autowired
    private PedidoService pedidoService;

    @GetMapping("/pedidos")
    public Iterable<PedidoDTO> listarLivros(){
        return pedidoService.listarPedidos();
    }

    @PostMapping("/salva-pedido")
    public ResponseEntity<Long> cadastrarPedido(@RequestBody PedidoDTO pedidoDTO){
        return pedidoService.salvarPedido(pedidoDTO);
    }

    @GetMapping("/pedidos-usuario/{email}")
    public List<PedidoEntregueDTO> retornarPedidosEntregues(@PathVariable String email){
        return pedidoService.listarLivrosPedidosPorUsuario(email);
    }

    @GetMapping("/pedidos-pendentes/{email}")
    public List<PedidoPendenteDTO> retornarPedidosPendentes(@PathVariable String email){
        return pedidoService.listarLivrosPendentes(email);
    }
 }
