package com.br.readnow.api.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.br.readnow.api.dto.LivroItemCarrinhoDTO;
import com.br.readnow.api.model.CarrinhoModel;
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

    public ResponseEntity<List<LivroItemCarrinhoDTO>> mostrarItensCarrinho(String token) {
        String email = tokenService.validarToken(token);

        Optional<UsuarioModel> usuarioOptional = usuarioRepository.findByEmail(email);

        if (usuarioOptional.isPresent()) {
            UsuarioModel usuario = usuarioOptional.get();
            Optional<CarrinhoModel> carrinho = carrinhoRepository.findByClienteCodigo(usuario.getCodigo());
            List<LivroItemCarrinhoModel> livrosCarrinho = livroItemCarrinhoRepository
                    .findAllByCarrinhoCodigo(carrinho.get().getCodigo());
            List<LivroItemCarrinhoDTO> itens = new ArrayList<>();

            for (LivroItemCarrinhoModel item : livrosCarrinho) {
                LivroItemCarrinhoDTO itemLivro = new LivroItemCarrinhoDTO();
                itemLivro.setEmail(email);
                itemLivro.setLivro(item.getLivro());
                itemLivro.setQuantidade(1);
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

    public void armazenaEstadoCarrinho(List<LivroItemCarrinhoDTO> livrosItemCarrinhoDTO) {
        Optional<CarrinhoModel> carrinhoOptional = null;
        for (LivroItemCarrinhoDTO item : livrosItemCarrinhoDTO) {
            Optional<UsuarioModel> usuarioOptional = usuarioRepository.findByEmail(item.getEmail());
            if (usuarioOptional.isPresent()) {
                carrinhoOptional = carrinhoRepository
                        .findByClienteCodigo(usuarioOptional.get().getCodigo());
                if (carrinhoOptional.isPresent()) {
                    Optional<LivroItemCarrinhoModel> livroOptional = livroItemCarrinhoRepository
                            .findByLivroCodigo(item.getLivro().getCodigo());
                    if (livroOptional.isPresent()) {
                        livroOptional.get().setQuantidade(item.getQuantidade());
                    } else {
                        LivroItemCarrinhoModel itemCarrinhoModel = new LivroItemCarrinhoModel();
                        itemCarrinhoModel.setCarrinho(carrinhoOptional.get());
                        itemCarrinhoModel.setLivro(item.getLivro());
                        livroItemCarrinhoRepository.save(itemCarrinhoModel);
                    }
                }
            }
        }
        if (carrinhoOptional != null) {
            List<LivroItemCarrinhoModel> livrosNoCarrinho = livroItemCarrinhoRepository
                    .findAllByCarrinhoCodigo(carrinhoOptional.get().getCodigo());

            for (LivroItemCarrinhoModel livroNoCarrinho : livrosNoCarrinho) {
                boolean livroPresenteNoDTO = livrosItemCarrinhoDTO.stream()
                        .anyMatch(itemDTO -> itemDTO.getLivro().getCodigo()
                                .equals(livroNoCarrinho.getLivro().getCodigo()));

                if (!livroPresenteNoDTO) {
                    // O livro está no carrinho, mas não está em livrosItemCarrinhoDTO, então remove
                    // do carrinho
                    livroItemCarrinhoRepository.delete(livroNoCarrinho);
                }
            }
        }
    }

    public ResponseEntity<?> removerItemCarrinho(Long livroId, String token) {
        String email = tokenService.validarToken(token);

        Optional<UsuarioModel> usuarioOptional = usuarioRepository.findByEmail(email);

        if (usuarioOptional.isPresent()) {
            Optional<CarrinhoModel> carrinhoOptional = carrinhoRepository
                    .findByClienteCodigo(usuarioOptional.get().getCodigo());

            if (carrinhoOptional.isPresent()) {
                CarrinhoModel carrinho = carrinhoOptional.get();
                Optional<LivroModel> livroOptional = livroRepository.findById(livroId);

                if (livroOptional.isPresent()) {
                    Optional<LivroItemCarrinhoModel> item = livroItemCarrinhoRepository.findByLivroCodigo(livroId);
                    Long codigoItem = item.get().getCodigo();
                    livroItemCarrinhoRepository.deleteById(item.get().getCodigo());
                    carrinhoRepository.save(carrinho);

                    return ResponseEntity.ok(codigoItem);
                } else {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Livro não encontrado!");
                }
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Carrinho não encontrado para o usuário!");
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado!");
        }
    }

    public ResponseEntity<?> adicionarItemAoCarrinho(Long livroId, int quantidade, String token) {
        String email = tokenService.validarToken(token);

        Optional<UsuarioModel> usuarioOptional = usuarioRepository.findByEmail(email);

        if (usuarioOptional.isPresent()) {
            Optional<CarrinhoModel> carrinhoOptional = carrinhoRepository
                    .findByClienteCodigo(usuarioOptional.get().getCodigo());

            if (carrinhoOptional.isPresent()) {
                Optional<LivroModel> livroOptional = livroRepository.findById(livroId);

                if (livroOptional.isPresent()) {
                    LivroModel livro = livroOptional.get();
                    LivroItemCarrinhoDTO livroItemCarrinhoDTO = new LivroItemCarrinhoDTO();

                    livroItemCarrinhoDTO.setEmail(email);
                    livroItemCarrinhoDTO.setLivro(livro);
                    livroItemCarrinhoDTO.setQuantidade(quantidade);

                    return ResponseEntity.status(HttpStatus.CREATED).body(livroItemCarrinhoDTO);
                } else {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Livro não encontrado!");
                }
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Carrinho não encontrado para o usuário!");
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado!");
        }
    }
}
