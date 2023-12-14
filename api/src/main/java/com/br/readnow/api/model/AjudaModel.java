package com.br.readnow.api.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "ajuda")
@Getter
@Setter
public class AjudaModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long codigo;
    
    private String titulo;
    private String historia;
    private String opcao;
    private boolean respondido;

    @ManyToOne
    @JoinColumn(name = "usuario_id") 
    private UsuarioModel usuario;
}
