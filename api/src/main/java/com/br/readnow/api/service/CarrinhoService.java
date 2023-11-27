package com.br.readnow.api.service;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import com.br.readnow.api.model.CarrinhoModel;

import com.br.readnow.api.repository.CarrinhoRepository;

@Service
public class CarrinhoService {
    @Autowired
    private CarrinhoRepository carrinhoRepository;

    public Iterable<CarrinhoModel> mostrarItensCarrinho() {
        return carrinhoRepository.findAll();
    }

}
