package com.br.readnow.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.br.readnow.api.service.CarrinhoService;

@RestController
public class CarrinhoController {

    @Autowired
    private CarrinhoService carrinhoService;

    // @GetMapping("/busca-carrinho")
    // public Iterable<CarrinhoModel> mostrarCarrinho() {
    //     return carrinhoService.mostrarItensCarrinho();
    // }

    @PostMapping("/adiciona-carrinho")
    public ResponseEntity<?> adicionarNoCarrinho(@RequestParam Long carrinhoId, @RequestParam  Long livroId, @RequestParam int quantidade) {
        return carrinhoService.adicionarItemCarrinho(carrinhoId, livroId, quantidade);
    }


}
