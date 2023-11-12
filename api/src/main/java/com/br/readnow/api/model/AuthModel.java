package com.br.readnow.api.model;

import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "auth")
@Getter
@Setter
public class AuthModel {

    @Id
    private UUID uuid;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private UsuarioModel usuario;

}
