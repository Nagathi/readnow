package com.br.readnow.api.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LivroInfoDTO {
    private Long codigo;
    private String imagem;
    private String titulo;
    private String autor;
    private String editora;
    private String isbn;
    private String descricao;
    private String data_publi;
    private String categoria;  
    private double preco;
    private double nota;
}
