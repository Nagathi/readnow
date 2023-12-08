package com.br.readnow.api.model;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "cartao")
@Getter
@Setter
public class CartaoModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long codigo;

    private String nome;
    private String numero;
    private String data;

    @ManyToOne
    @JoinColumn(name = "usuario_id") 
    private UsuarioModel usuario;

    @OneToMany(mappedBy = "cartao", cascade = CascadeType.ALL)
    private List<PedidoModel> pedidos;
}
