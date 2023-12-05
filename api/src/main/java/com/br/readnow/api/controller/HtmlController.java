package com.br.readnow.api.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HtmlController {
    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/login")
    public String logar() {
        return "login";
    }

    @GetMapping("/cadastro-cliente")
    public String cadastrar() {
        return "cadastro-cliente";
    }

    @GetMapping("/esqueci-minha-senha")
    public String recuperarSenha() {
        return "formulario-recuperar-senha";
    }

    @GetMapping("/livro")
    public String mostrarLivro() {
        return "livro";
    }

    @GetMapping("/alterar-senha")
    public String alterarSenha() {
        return "formulario-nova-senha";
    }

    @GetMapping("/cadastrado")
    public String usuarioCadastrado() {
        return "agradecimento-cadastro";
    }

    @GetMapping("/carrinho")
    public String mostrarCarrinho() {
        return "carrinho-compras";
    }

    @GetMapping("/carteira")
    public String mostrarCarteira() {
        return "carteira";
    }

    @GetMapping("/central-ajuda")
    public String mostrarCentralAjuda() {
        return "central-ajuda";
    }

    @GetMapping("/conta-usuario")
    public String mostrarContaUsuario() {
        return "conta-usuario";
    }

    @GetMapping("/enderecos")
    public String mostrarEnderecos() {
        return "enderecos";
    }

    @GetMapping("/formulario-cartao")
    public String mostrarFormCartao() {
        return "formulario-cartao";
    }

    @GetMapping("/formulario-ajuda")
    public String mostrarFormCentralAjuda() {
        return "formulario-central-ajuda";
    }

    @GetMapping("/formulario-endereco")
    public String mostrarFormEndereco() {
        return "formulario-endereco";
    }

    @GetMapping("/perfil-cliente")
    public String mostrarPerfilUsuario() {
        return "perfil-cliente";
    }

    @GetMapping("/finalizar-pedido")
    public String finalizarPedido() {
        return "finalizar-pedido";
    }

    @GetMapping("/editar-endereco")
    public String editarEndereco() {
        return "editar-endereco";
    }

    @GetMapping("/resultado-pesquisa")
    public String pesquisar() {
        return "resultado-pesquisa";
    }

}
