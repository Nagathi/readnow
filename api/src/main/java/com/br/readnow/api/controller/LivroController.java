package com.br.readnow.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.br.readnow.api.dto.AvaliacaoDTO;
import com.br.readnow.api.dto.LivroDTO;
import com.br.readnow.api.model.LivroModel;
import com.br.readnow.api.service.LivroService;

@RestController
public class LivroController {
    
    @Autowired
    private LivroService livroService;

    @GetMapping("/livros")
    public Iterable<LivroModel> listarLivros(){
        return livroService.listarLivros();
    }

    @GetMapping("/livros/{categoria}")
    public ResponseEntity<List<LivroModel>> listarLivrosPorCategoria(@PathVariable String categoria) {
        List<LivroModel> livros = livroService.listarLivrosPorCategoria(categoria);
        return ResponseEntity.ok(livros);
    }

    @PostMapping(value = "/cadastra-livro", consumes = "multipart/form-data")
    public ResponseEntity<?> cadastrarLivro(@RequestPart(value = "imagem") MultipartFile imagem,
                                            @RequestParam(value = "titulo") String titulo,
                                            @RequestParam(value = "autor") String autor,
                                            @RequestParam(value = "editora") String editora,
                                            @RequestParam(value = "isbn") String isbn,
                                            @RequestParam(value = "data_publi") String data_publi,
                                            @RequestParam(value = "categoria") String categoria,
                                            @RequestParam(value = "descricao") String descricao,
                                            @RequestParam(value = "preco") double preco){
        LivroDTO livro = new LivroDTO();
        livro.setImagem(imagem);
        livro.setTitulo(titulo);
        livro.setAutor(autor);
        livro.setEditora(editora);
        livro.setIsbn(isbn);
        livro.setData_publi(data_publi);
        livro.setCategoria(categoria);
        livro.setDescricao(descricao);
        livro.setPreco(preco);
        return livroService.cadastrarLivro(livro);
    }

    @GetMapping("/busca-livro/{codigo}")
    public ResponseEntity<?> buscarLivro(@PathVariable long codigo){
        return livroService.buscarLivro(codigo);
    }

    @GetMapping("/pesquisa")
    public ResponseEntity<List<LivroModel>> buscarLivro(@RequestParam(value = "palavra") String pesquisa){
        return livroService.pesquisar(pesquisa);
    }

    @PostMapping("/avaliacao")
    public ResponseEntity<?> realizarAvaliacao(@RequestBody AvaliacaoDTO avaliacaoDTO) {
        return livroService.realizarAvaliacao(avaliacaoDTO.getEmail(), avaliacaoDTO.getCodigo(), avaliacaoDTO.getQtdEstrelas(), avaliacaoDTO.getDescricao());
    }
}
