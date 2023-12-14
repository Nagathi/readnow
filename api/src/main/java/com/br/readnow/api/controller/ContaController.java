package com.br.readnow.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.br.readnow.api.dto.CartaoDTO;
import com.br.readnow.api.dto.EnderecoDTO;
import com.br.readnow.api.dto.FormAjudaDTO;
import com.br.readnow.api.dto.RequestDeleteDTO;
import com.br.readnow.api.service.ContaService;

@RestController
public class ContaController {

    @Autowired
    private ContaService contaService;

    @GetMapping("/enderecos/{token}")
    public ResponseEntity<List<EnderecoDTO>> listarEnderecosPorEmail(@PathVariable String token){
        return contaService.listarEnderecosPorEmail(token);
    }

    @PostMapping("/cadastra-endereco")
    public ResponseEntity<?> cadastrarEndereco(@RequestBody EnderecoDTO endereco){
        return contaService.cadastrarEndereco(endereco);
    }

    @PutMapping("/edita-endereco")
    public ResponseEntity<?> editarEndereco(@RequestBody EnderecoDTO endereco){
        return contaService.editarEndereco(endereco);
    }

    @DeleteMapping("/exclui-endereco/{codigo}")
    public ResponseEntity<?> excluirEndereco(@PathVariable Long codigo){
        return contaService.excluirEndereco(codigo);
    }

    @GetMapping("/endereco/{codigo}")
    public ResponseEntity<EnderecoDTO> buscarEnderecoPorCodigo(@PathVariable Long codigo){
        return contaService.buscarEnderecoPorCodigo(codigo);
    }

    @GetMapping("/cartoes/{token}")
    public ResponseEntity<List<CartaoDTO>> listarCartoesPorEmail(@PathVariable String token){
        return contaService.listarCartoesPorEmail(token);
    }

    @PostMapping("/cadastra-cartao")
    public ResponseEntity<?> cadastrarCartao(@RequestBody CartaoDTO cartao){
        return contaService.cadastrarCartao(cartao);
    }

    @PutMapping("/edita-cartao")
    public ResponseEntity<?> editarCartao(@RequestBody CartaoDTO cartaoDTO){
        return contaService.editarCartao(cartaoDTO);
    }

    @DeleteMapping("/exclui-cartao")
    public ResponseEntity<?> excluirCartao(@RequestBody RequestDeleteDTO requestDeleteDTO){
        return contaService.excluirCartao(requestDeleteDTO);
    }

    @PostMapping("/email-ajuda")
    public ResponseEntity<?> enviarEmailDeAjuda(@RequestBody FormAjudaDTO ajuda){
        return contaService.enviarEmailDeAjuda(ajuda);
    }

}
