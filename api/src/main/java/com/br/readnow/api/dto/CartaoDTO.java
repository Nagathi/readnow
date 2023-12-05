package com.br.readnow.api.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartaoDTO {
    private long codigo;
    private String nome;
    private String numero;
    private String data;
    private String email;
}
