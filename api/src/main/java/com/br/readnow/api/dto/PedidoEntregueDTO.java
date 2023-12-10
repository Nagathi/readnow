package com.br.readnow.api.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PedidoEntregueDTO {
    private Long codigo;
    private Long codigoLivro;
    private String imagem;
    private String titulo;
    private String data;
}
