package com.br.readnow.api.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RequestNovaSenhaDTO {
    private String usuario;
    private String senha;
}
