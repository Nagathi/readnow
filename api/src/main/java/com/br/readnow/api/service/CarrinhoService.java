package com.br.readnow.api.service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.br.readnow.api.model.CarrinhoModel;
import com.br.readnow.api.model.LivroItemCarrinhoModel;
import com.br.readnow.api.model.LivroModel;
import com.br.readnow.api.model.UsuarioModel;
import com.br.readnow.api.repository.CarrinhoRepository;
import com.br.readnow.api.repository.LivroItemCarrinhoRepository;
import com.br.readnow.api.repository.LivroRepository;
import com.br.readnow.api.repository.UsuarioRepository;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class CarrinhoService {
    // @Autowired
    // private CarrinhoRepository carrinhoRepository;
    // @Autowired
    // private LivroItemCarrinhoRepository livroItemCarrinhoRepository;

    // @Autowired
    // private LivroRepository livroRepository;

    // @Autowired
    // private UsuarioRepository usuarioRepository;

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

    public Iterable<CarrinhoModel> mostrarItensCarrinho() {
        return carrinhoRepository.findAll();
    }

    public ResponseEntity<String> adicionarItemAoCarrinho(Long livroId, int quantidade, HttpServletRequest request) {
        String token = getTokenFromRequest(request);

        if (token != null && tokenService.validarToken(token) != null) {
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
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token inválido ou expirado!");
        }
    }

    private String getTokenFromRequest(HttpServletRequest request) {
        String token = request.getHeader("Authorization");

        if (StringUtils.hasText(token) && token.startsWith("Bearer ")) {
            return token.substring(7);
        }

        return null;
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
