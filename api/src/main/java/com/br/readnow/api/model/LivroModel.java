package com.br.readnow.api.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Table(name = "livro")
@Getter
@Setter
@Entity
public class LivroModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long codigo;

    private String imagem;
    private String titulo;
    private String autor;
    private String editora;
    private String isbn;
    @Lob
    @Column(columnDefinition = "TEXT")
    private String descricao;
    private String data_publi;
    private String categoria;  
    private double preco;
}
