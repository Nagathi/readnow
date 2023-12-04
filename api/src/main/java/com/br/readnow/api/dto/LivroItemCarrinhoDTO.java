package com.br.readnow.api.dto;

import com.br.readnow.api.model.LivroModel;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LivroItemCarrinhoDTO {
    private String email;
    private LivroModel livro;
    private int quantidade;
}
