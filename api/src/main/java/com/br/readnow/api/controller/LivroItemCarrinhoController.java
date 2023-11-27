package com.br.readnow.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.br.readnow.api.service.LivroItemCarrinhoService;

@RestController
public class LivroItemCarrinhoController {

    @Autowired
    private LivroItemCarrinhoService livroItemCarrinhoService;

    @PostMapping("/adiciona-carrinho/{carrinhoId}/livro/{livroId}")
    public ResponseEntity<?> adicionarNoCarrinho(@PathVariable Long carrinhoId, @PathVariable Long livroId,
            @RequestParam int quantidade) {
        return livroItemCarrinhoService.adicionarItem(carrinhoId, livroId, quantidade);
    }
}
