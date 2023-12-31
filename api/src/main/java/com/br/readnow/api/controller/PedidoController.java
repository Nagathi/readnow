package com.br.readnow.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.br.readnow.api.dto.AjudaDTO;
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
    public ResponseEntity<Long> cadastrarPedido(@RequestHeader("Authorization") String authorization, @RequestBody PedidoDTO pedidoDTO){
        String token = authorization.replace("Bearer ", "");

        return pedidoService.salvarPedido(token, pedidoDTO);
    }

    @GetMapping("/pedidos-usuario/{token}")
    public List<PedidoEntregueDTO> retornarPedidosEntregues(@PathVariable String token){
        return pedidoService.listarLivrosPedidosPorUsuario(token);
    }

    @GetMapping("/pedidos-pendentes/{token}")
    public List<PedidoPendenteDTO> retornarPedidosPendentes(@PathVariable String token){
        return pedidoService.listarLivrosPendentes(token);
    }

    @GetMapping("/pedidos-ajuda/{token}")
    public List<AjudaDTO> retornarPedidos(@PathVariable String token){
        return pedidoService.listarPedidos(token);
    }
 }
