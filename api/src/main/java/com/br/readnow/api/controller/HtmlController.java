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

    @GetMapping("/cadastro")
    public String cadastrar() {
        return "new_user";
    }

    @GetMapping("/esqueci-minha-senha")
    public String recuperarSenha() {
        return "formulario-email";
    }

    @GetMapping("/livro")
    public String mostrarLivro() {
        return "livro";
    }

    @GetMapping("/alterar-senha")
    public String alterarSenha() {
        return "formulario-senha";
    }

    @GetMapping("/cadastrado")
    public String usuarioCadastrado() {
        return "agradecimento-cadastro";
    }

    @GetMapping("/404")
    public String erro404() {
        return "404";
    }
}
