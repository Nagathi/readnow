package com.br.readnow.api.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EnderecoDTO {
    private long codigo;
    private String email;
    private String nomeDestino;
    private String logradouro;
    private String telefone;
    private String bairro;
    private String numeroCasa;
    private String cep;
    private String complemento;
    private String cidade;
    private String estado;
    private String pais;
}
