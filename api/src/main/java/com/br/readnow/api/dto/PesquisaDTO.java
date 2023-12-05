package com.br.readnow.api.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PesquisaDTO {
    private String titulo;
    private String autor;
    private String editora;
    private String isbn;
    private String categoria;
}
