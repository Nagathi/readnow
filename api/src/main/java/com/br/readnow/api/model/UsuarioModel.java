package com.br.readnow.api.model;

import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "usuario")
@Getter
@Setter
public class UsuarioModel {
    @Id
    private UUID id;

    private String nome;
    private String email;
    private String usuario;
    private String senha;
    private String data_nasc;
}
