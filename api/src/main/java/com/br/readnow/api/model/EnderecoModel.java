package com.br.readnow.api.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "endereco")
@Getter
@Setter
public class EnderecoModel {
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        private Long codigo;
    
        private String logradouro;
        private String bairro;
        private String numeroCasa;
        private String cep;
        private String complemento;
        private String cidade;
        private String estado;
        private String pais;

    
         
}
