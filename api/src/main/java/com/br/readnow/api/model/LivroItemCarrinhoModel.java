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

@Table(name = "livroCarrinho")
@Getter
@Setter
@Entity
public class LivroItemCarrinhoModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long codigo;

    @ManyToOne
    @JoinColumn(name = "livro_codigo")
    private LivroModel livro;
    private int quantidade;

    @ManyToOne
    @JoinColumn(name = "carrinho_codigo")
    private CarrinhoModel carrinho;
}
