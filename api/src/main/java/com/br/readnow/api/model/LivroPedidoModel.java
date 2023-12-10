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
@Table(name = "livroPedido")
@Getter
@Setter
public class LivroPedidoModel {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long codigo;

    @ManyToOne
    @JoinColumn(name = "livro_codigo")
    private LivroModel livro;

    private int quantidade;

    @ManyToOne
    @JoinColumn(name = "pedido_id")
    private PedidoModel pedido;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private UsuarioModel usuario;
}
