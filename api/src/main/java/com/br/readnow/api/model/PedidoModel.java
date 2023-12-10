package com.br.readnow.api.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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
    private boolean entregue;

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<LivroItemModel> livros;

    @ManyToOne
    @JoinColumn(name = "cartao_id", unique = false) 
    private CartaoModel cartao;

    @ManyToOne
    @JoinColumn(name = "endereco_id", unique = false)
    private EnderecoModel endereco;

    @ManyToOne
    @JoinColumn(name = "usuario_id", unique = false)
    private UsuarioModel usuario;
}
