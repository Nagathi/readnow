package com.br.readnow.api.dto;

import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LivroDTO {
    private MultipartFile imagem;
    private String titulo;
    private String autor;
    private String editora;
    private String isbn;
    private String data_publi;
    private String categoria;
    private double preco;
}
