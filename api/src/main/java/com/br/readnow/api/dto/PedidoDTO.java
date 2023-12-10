package com.br.readnow.api.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PedidoDTO {
    private String dataPedido;
    private double valorTotal;
    private String dataEntregaPrevista;
    private String email;
    private Long codigoCartao;
    private Long codigoEndereco;
    private Long codigoLivro;
    private boolean entregue;
    private List<LivroQuantidadeDTO> livros;
}
