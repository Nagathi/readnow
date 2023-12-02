package com.br.readnow.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.br.readnow.api.dto.EnderecoDTO;
import com.br.readnow.api.service.ContaService;

@RestController
public class ContaController {

    @Autowired
    private ContaService contaService;

    @GetMapping("/enderecos/{email}")
    public ResponseEntity<List<EnderecoDTO>> listarEnderecosPorEmail(@PathVariable String email){
        return contaService.listarEnderecosPorEmail(email);
    }

    @PostMapping("/cadastra-endereco")
    public ResponseEntity<List<EnderecoDTO>> cadastrarEndereco(@RequestBody EnderecoDTO endereco){
        return contaService.cadastrarEndereco(endereco);
    }

}
