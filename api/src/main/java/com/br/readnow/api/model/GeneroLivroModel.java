package com.br.readnow.api.model;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Table(name = "genero_livro")
@Getter
@Setter
public class GeneroLivroModel {
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long codigo;

    private String nome;
}
