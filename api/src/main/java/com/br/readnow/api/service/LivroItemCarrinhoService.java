package com.br.readnow.api.service;

import java.util.Optional;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.br.readnow.api.model.CarrinhoModel;
import com.br.readnow.api.model.LivroItemCarrinhoModel;
import com.br.readnow.api.model.LivroModel;
import com.br.readnow.api.repository.CarrinhoRepository;
import com.br.readnow.api.repository.LivroItemCarrinhoRepository;
import com.br.readnow.api.repository.LivroRepository;

@Service
public class LivroItemCarrinhoService {

    @Autowired
    private LivroItemCarrinhoRepository livroItemCarrinhoRepository;

    @Autowired
    private LivroRepository livroRepository;

    @Autowired
    private CarrinhoRepository carrinhoRepository;

    public ResponseEntity<?> adicionarItem(Long carrinhoId, Long livroId, int quantidade) {
        Optional<LivroModel> livroModelOptional = livroRepository.findById(livroId);
        Optional<CarrinhoModel> carrinhoOption = carrinhoRepository.findById(carrinhoId);

        if (livroModelOptional.isEmpty() || carrinhoOption.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Livro ou Carrinho não encontrado!");
        }

        CarrinhoModel carrinho = carrinhoOption.get();
        List<LivroItemCarrinhoModel> itensCarrinho = livroItemCarrinhoRepository.findAllByLivroCodigo(livroId);

        LivroItemCarrinhoModel itemCarrinho = encontrarItemNoCarrinho(itensCarrinho, carrinho);

        if (itemCarrinho != null) {
            // Item já existe no carrinho, atualiza a quantidade
            itemCarrinho.setQuantidade(itemCarrinho.getQuantidade() + quantidade);
            carrinho.setValorTotal(carrinho.getValorTotal() + (livroModelOptional.get().getPreco() * quantidade));
        } else {
            // Item não existe no carrinho, cria um novo
            itemCarrinho = new LivroItemCarrinhoModel();
            itemCarrinho.setCarrinho(carrinho);
            itemCarrinho.setLivro(livroModelOptional.get());
            itemCarrinho.setQuantidade(quantidade);

            carrinho.getLivros().add(itemCarrinho);
            carrinho.setValorTotal(carrinho.getValorTotal() + (livroModelOptional.get().getPreco() * quantidade));
        }

        livroItemCarrinhoRepository.save(itemCarrinho);
        carrinhoRepository.save(carrinho);

        return ResponseEntity.status(HttpStatus.CREATED).body("Item adicionado!");


    }
    private LivroItemCarrinhoModel encontrarItemNoCarrinho(List<LivroItemCarrinhoModel> itens, CarrinhoModel carrinho) {
        return itens.stream()
                .filter(item -> item.getCarrinho().getCodigo().equals(carrinho.getCodigo()))
                .findFirst()
                .orElse(null);
    }
}
