package com.br.readnow.api.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FormAjudaDTO {
    private String token;
    private Long codigo;
    private String titulo;
    private String historia;
    private String opcao;
}
