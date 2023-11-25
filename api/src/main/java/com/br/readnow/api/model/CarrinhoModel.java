package com.br.readnow.api.model;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
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

    private Long codigoCliente;
    @OneToMany private List<LivroModel> livros;
    private int quantidadeLivros;
    private double valorTotal;
}
