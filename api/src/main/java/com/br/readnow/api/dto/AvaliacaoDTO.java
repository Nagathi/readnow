package com.br.readnow.api.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AvaliacaoDTO {
    private String email;
    private Long codigo;
    private String descricao;
    private int qtdEstrelas;
}
