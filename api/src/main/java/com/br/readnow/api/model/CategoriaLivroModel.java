package com.br.readnow.api.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Table(name = "categoriaLivro")
@Getter
@Setter
@Entity
public class CategoriaLivroModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long codigo;

    private String nome;
}
