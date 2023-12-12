package com.br.readnow.api.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AjudaDTO {
    private Long codigo;
    private String imagem;
    private String titulo;
    private String diaMes;
    private String ano;
}
