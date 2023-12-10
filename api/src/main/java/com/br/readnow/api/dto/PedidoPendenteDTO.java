package com.br.readnow.api.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PedidoPendenteDTO {
    private Long codigo;
    private String imagem;
    private String dataEntrega;
    private String data;
    private String titulo;
}
