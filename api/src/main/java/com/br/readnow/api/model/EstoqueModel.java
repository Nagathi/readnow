package com.br.readnow.api.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "estoque")
@Getter
@Setter
public class EstoqueModel {
    @Id
    private Long codigoLivro;
    private int quantidadeLivros;
}

