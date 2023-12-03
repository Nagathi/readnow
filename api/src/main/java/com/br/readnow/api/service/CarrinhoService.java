package com.br.readnow.api.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.br.readnow.api.dto.EnderecoDTO;
import com.br.readnow.api.model.CarrinhoModel;
import com.br.readnow.api.model.EnderecoModel;
import com.br.readnow.api.model.LivroItemCarrinhoModel;
import com.br.readnow.api.model.LivroModel;
import com.br.readnow.api.model.UsuarioModel;
import com.br.readnow.api.repository.CarrinhoRepository;
import com.br.readnow.api.repository.LivroItemCarrinhoRepository;
import com.br.readnow.api.repository.LivroRepository;
import com.br.readnow.api.repository.UsuarioRepository;


@Service
public class CarrinhoService {

    @Autowired
    private LivroItemCarrinhoRepository livroItemCarrinhoRepository;

    @Autowired
    private CarrinhoRepository carrinhoRepository;

    @Autowired
    private LivroRepository livroRepository;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public ResponseEntity<List<LivroItemCarrinhoModel>> mostrarItensCarrinho(String email) {
        Optional<UsuarioModel> usuarioOptional = usuarioRepository.findByEmail(email);

        if(usuarioOptional.isPresent()){
            UsuarioModel usuario = usuarioOptional.get();
            Optional<CarrinhoModel> carrinho = carrinhoRepository.findByClienteCodigo(usuario.getCodigo());
            List<LivroItemCarrinhoModel> livrosCarrinho = livroItemCarrinhoRepository.findAllByCarrinhoCodigo(carrinho.get().getCodigo());
            List<LivroItemCarrinhoModel> itens = new ArrayList<>();

            for (LivroItemCarrinhoModel item : livrosCarrinho) {
                LivroItemCarrinhoModel itemLivro = new LivroItemCarrinhoModel();
                itemLivro.setLivro(item.getLivro());
                itemLivro.setQuantidade(item.getQuantidade());
                itemLivro.setCodigo(item.getCodigo());
                itemLivro.setCarrinho(item.getCarrinho());
                itens.add(itemLivro);
            }

            if (!itens.isEmpty()) {
                return ResponseEntity.ok(itens);
            } else {
                return ResponseEntity.noContent().build();
            }
        } else {
            return ResponseEntity.notFound().build();
        }


    }

    public ResponseEntity<String> adicionarItemAoCarrinho(Long livroId, int quantidade, String token) {

        // if (token != null && tokenService.validarToken(token) != null) {
            String email = tokenService.validarToken(token);

            Optional<UsuarioModel> usuarioOptional = usuarioRepository.findByEmail(email);

            if (usuarioOptional.isPresent()) {
                Optional<CarrinhoModel> carrinhoOptional = carrinhoRepository
                        .findByClienteCodigo(usuarioOptional.get().getCodigo());

                if (carrinhoOptional.isPresent()) {
                    CarrinhoModel carrinho = carrinhoOptional.get();
                    Optional<LivroModel> livroOptional = livroRepository.findById(livroId);

                    if (livroOptional.isPresent()) {
                        LivroModel livro = livroOptional.get();

                        List<LivroItemCarrinhoModel> itensCarrinho = livroItemCarrinhoRepository
                                .findAllByLivroCodigo(livroId);

                        LivroItemCarrinhoModel itemCarrinho = encontrarItemNoCarrinho(itensCarrinho, carrinho);

                        if (itemCarrinho != null) {
                            // Item já existe no carrinho, atualiza a quantidade
                            itemCarrinho.setQuantidade(itemCarrinho.getQuantidade() + quantidade);
                            carrinho.setValorTotal(carrinho.getValorTotal() + livro.getPreco());
                        } else {
                            // Item não existe no carrinho, cria um novo
                            itemCarrinho = new LivroItemCarrinhoModel();
                            itemCarrinho.setCarrinho(carrinho);
                            itemCarrinho.setLivro(livro);
                            itemCarrinho.setQuantidade(quantidade);
                            carrinho.getLivros().add(itemCarrinho);
                            carrinho.setValorTotal(carrinho.getValorTotal() + livro.getPreco());
                        }

                        livroItemCarrinhoRepository.save(itemCarrinho);
                        carrinhoRepository.save(carrinho);

                        return ResponseEntity.status(HttpStatus.CREATED).body("Item adicionado ao carrinho!");
                    } else {
                        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Livro não encontrado!");
                    }
                } else {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Carrinho não encontrado para o usuário!");
                }
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado!");
            }
        // } else {
        //     return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token inválido ou expirado!");
        // }
    }

    private LivroItemCarrinhoModel encontrarItemNoCarrinho(List<LivroItemCarrinhoModel> itensCarrinho,
            CarrinhoModel carrinho) {
        // Implemente a lógica para encontrar o item no carrinho, se necessário
        // Por exemplo, pode ser feita uma busca por livro ou outra lógica de comparação
        // Aqui vou apenas iterar sobre os itens
        for (LivroItemCarrinhoModel item : itensCarrinho) {
            if (Objects.equals(item.getCarrinho().getCodigo(), carrinho.getCodigo())) {
                return item;
            }
        }

        return null;
    }

}
