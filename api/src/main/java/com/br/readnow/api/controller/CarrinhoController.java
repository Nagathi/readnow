package com.br.readnow.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.br.readnow.api.service.CarrinhoService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
public class CarrinhoController {

    @Autowired
    private CarrinhoService carrinhoService;

    @PostMapping("/adiciona-livro")
    public ResponseEntity<?> adicionarNoCarrinho(@RequestParam Long livroId,
            @RequestParam int quantidade, HttpServletRequest principal) {
        return carrinhoService.adicionarItemAoCarrinho(livroId, quantidade, principal);
    }

}
