package com.br.readnow.api.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "carrinho")
@Getter
@Setter
public class CarrinhoModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long codigo;


    @OneToOne
    @JoinColumn(name = "cliente_codigo")
    private UsuarioModel cliente;

    @OneToMany(mappedBy = "carrinho", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<LivroItemCarrinhoModel> livros = new ArrayList<>();
    private double valorTotal;

    public void criarCarrinho(UsuarioModel cliente) {
        this.cliente = cliente;
    }

}
