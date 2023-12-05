package com.br.readnow.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.br.readnow.api.dto.LivroItemCarrinhoDTO;
import com.br.readnow.api.service.CarrinhoService;

@RestController
public class CarrinhoController {

    @Autowired
    private CarrinhoService carrinhoService;

    @PostMapping("/adiciona-livro")
    public ResponseEntity<?> adicionarNoCarrinho(@RequestParam("codigo") Long codigo,
            @RequestParam("quantidade") int quantidade, @RequestHeader("Authorization") String authorization) {
        String token = authorization.replace("Bearer ", "");

        return carrinhoService.adicionarItemAoCarrinho(codigo, quantidade, token);
    }

    @PostMapping("/remove-livro")
    public ResponseEntity<?> removerDoCarrinho(@RequestParam("codigo") Long codigo,
            @RequestHeader("Authorization") String authorization) {
        String token = authorization.replace("Bearer ", "");

        return carrinhoService.removerItemCarrinho(codigo, token);
    }

    @GetMapping("/mostra-carrinho/{token}")
    public ResponseEntity<?> mostrarLivrosCarrinho(@PathVariable String token) {
        return carrinhoService.mostrarItensCarrinho(token);

    }

    @PostMapping("/atualiza-carrinho")
    public void atualizaEstadoCarrinho(@RequestBody List<LivroItemCarrinhoDTO> livros) {
        carrinhoService.armazenaEstadoCarrinho(livros);

    }

}
