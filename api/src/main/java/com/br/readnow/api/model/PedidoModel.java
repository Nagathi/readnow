package com.br.readnow.api.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "pedido")
@Getter
@Setter
public class PedidoModel {
    @Id    
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long codigo;

    private String dataPedido;
    private double valorTotal;
    private String dataEntregaPrevista;
    private Long codigoCliente;
    private Long codigoCarrinho;
    private Long codigoFormaPagamento;
}
