package com.br.readnow.api.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "livro")
@Getter
@Setter
public class LivroModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long codigo;

    private String imagem;
    private String titulo;
    private String autor;
    private String editora;
    private String isbn;
    private String data_publi;
    private String categoria;  
    private double preco;
}
