package com.br.readnow.api.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.br.readnow.api.model.CarrinhoModel;
import com.br.readnow.api.model.LivroCarrinhoModel;
import com.br.readnow.api.model.LivroModel;
import com.br.readnow.api.repository.CarrinhoRepository;
import com.br.readnow.api.repository.LivroCarrinhoRepository;
import com.br.readnow.api.repository.LivroRepository;

@Service
public class CarrinhoService {
    @Autowired
    private CarrinhoRepository carrinhoRepository;

    @Autowired
    private LivroRepository livroRepository;

    @Autowired
    private LivroCarrinhoRepository livroCarrinhoRepository;

    public Iterable<CarrinhoModel> mostrarItensCarrinho() {
        return carrinhoRepository.findAll();
    }

    public void criarCarrinho() {
        CarrinhoModel carrinho = new CarrinhoModel();
        carrinhoRepository.save(carrinho);
    }

    public ResponseEntity<?> adicionarItemCarrinho(Long carrinhoId, Long livroId, int quantidade) {
        criarCarrinho();
        CarrinhoModel carrinho = carrinhoRepository.findById(carrinhoId)
                .orElseThrow(() -> new RuntimeException("Carrinho não encontrado"));

        LivroModel livro = livroRepository.findById(livroId)
                .orElseThrow(() -> new RuntimeException("Livro não encontrado"));

        // // Verifica se o produto já está no carrinho
        // LivroCarrinhoModel carrinhoItemExistente = carrinho.getItens().stream()
        //         .filter(item -> item.getProduto().equals(produto))
        //         .findFirst()
        //         .orElse(null);

        // if (carrinhoItemExistente != null) {
        //     // Se o produto já estiver no carrinho, apenas atualize a quantidade
        //     carrinhoItemExistente.setQuantidade(carrinhoItemExistente.getQuantidade() + quantidade);
        // } else {
            // Caso contrário, crie um novo item de carrinho
            LivroCarrinhoModel novoItem = new LivroCarrinhoModel();
            novoItem.setLivro(livro);
            novoItem.setQuantidade(quantidade);
            carrinho.setCodigoCliente("1f52366d-bfa6-4ece-ad7b-1cd5c5875597");
            carrinho.getLivros().add(novoItem);
        // }

        // Atualiza e salva o carrinho
        carrinhoRepository.save(carrinho);


    // Outros métodos de serviço relacionados ao carrinho...
        return ResponseEntity.status(HttpStatus.CREATED).body("item adicionado!");
    }

}
