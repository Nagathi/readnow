package com.br.readnow.api.dto;

import com.br.readnow.api.model.UserRoleModel;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioDTO {
    private String nome;
    private String token;
    private UserRoleModel tipo;
}
