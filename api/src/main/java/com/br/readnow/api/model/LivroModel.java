package com.br.readnow.api.model;

import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "livro")
@Getter
@Setter
public class LivroModel {
    @Id
    private UUID id;
    
}
