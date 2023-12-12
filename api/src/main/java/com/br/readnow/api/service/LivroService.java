package com.br.readnow.api.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.br.readnow.api.dto.LivroDTO;
import com.br.readnow.api.dto.LivroInfoDTO;
import com.br.readnow.api.model.AvaliacaoModel;
import com.br.readnow.api.model.LivroModel;
import com.br.readnow.api.model.UsuarioModel;
import com.br.readnow.api.repository.AvaliacaoRepository;
import com.br.readnow.api.repository.LivroPedidoRepository;
import com.br.readnow.api.repository.LivroRepository;
import com.br.readnow.api.repository.UsuarioRepository;

@Service
public class LivroService {

    @Autowired
    private LivroRepository livroRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private AvaliacaoRepository avaliacaoRepository;

    @Autowired
    private LivroPedidoRepository livroPedidoRepository;

    public Iterable<LivroModel> listarLivros() {
        return livroRepository.findAll();
    }

    public List<LivroModel> listarLivrosPorCategoria(String categoria) {
        return livroRepository.findByCategoria(categoria);
    }

    public ResponseEntity<?> cadastrarLivro(LivroDTO livroDTO) {

        LivroModel livro = new LivroModel();

        try {
            if (!livroRepository.existsByIsbn(livroDTO.getIsbn())) {
                MultipartFile imagem = livroDTO.getImagem();

                // C:/Users/gu-gu/OneDrive/Documentos/Eng. de Computação/Desenvolvimento
                // Web/Trabalho 1/API/api/src/main/resources/static/images/livros/
                // /home/victoria/Documentos/UFPA/Optativas/DesenvolvimentoWeb/ReadNow/api/src/main/resources/static/images/livros/

                // C:/Users/elise/Documents/UFPA/desenvolvimento_web/readnow/api/src/main/resources/static/images/livros/

                String diretorioAtual = System.getProperty("user.dir");
                String caminhoRelativo = "/api/src/main/resources/static/images/livros/";
                String caminhoAbsoluto = diretorioAtual.toString() + caminhoRelativo;
                String caminhoFinal = caminhoAbsoluto.toString();

                String uploadImagem = caminhoFinal;
                String uniqueImageName = UUID.randomUUID().toString() + "_" + imagem.getOriginalFilename();

                Path destinoImagem = Path.of(uploadImagem + uniqueImageName);
                Files.copy(imagem.getInputStream(), destinoImagem, StandardCopyOption.REPLACE_EXISTING);

                livro.setImagem(uniqueImageName);
                livro.setTitulo(livroDTO.getTitulo());
                livro.setAutor(livroDTO.getAutor());
                livro.setEditora(livroDTO.getEditora());
                livro.setIsbn(livroDTO.getIsbn());
                livro.setData_publi(livroDTO.getData_publi());
                livro.setCategoria(livroDTO.getCategoria());
                livro.setDescricao(livroDTO.getDescricao());
                livro.setPreco(livroDTO.getPreco());

                livroRepository.save(livro);
                return ResponseEntity.status(HttpStatus.CREATED).body("Livro cadastrado!");
            } else {
                return ResponseEntity.badRequest().body("Esse ISBN já existe em nosso banco de dados!");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return ResponseEntity.badRequest().build();
    }

    public ResponseEntity<?> buscarLivro(long codigo) {

        if (livroRepository.findById(codigo).isPresent()) {
            LivroModel livro = livroRepository.findById(codigo).get();
            LivroInfoDTO livroInfoDTO = new LivroInfoDTO();
            livroInfoDTO.setCodigo(livro.getCodigo());
            livroInfoDTO.setImagem(livro.getImagem());
            livroInfoDTO.setTitulo(livro.getTitulo());
            livroInfoDTO.setAutor(livro.getAutor());
            livroInfoDTO.setEditora(livro.getEditora());
            livroInfoDTO.setIsbn(livro.getIsbn());
            livroInfoDTO.setDescricao(livro.getDescricao());
            livroInfoDTO.setData_publi(livro.getData_publi());
            livroInfoDTO.setCategoria(livro.getCategoria());
            livroInfoDTO.setPreco(livro.getPreco());
            double nota = this.calcularMediaAvaliacao(livro.getCodigo());

            if (nota != 0) {
                livroInfoDTO.setNota(nota);
            } else {
                livroInfoDTO.setNota(5d);
            }

            return ResponseEntity.ok(livroInfoDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    public ResponseEntity<List<LivroModel>> pesquisar(String pesquisa) {
        List<LivroModel> resultados = livroRepository
                .findByTituloContainingOrAutorContainingOrEditoraContainingOrIsbnContainingOrCategoriaContaining(
                        pesquisa, pesquisa, pesquisa, pesquisa, pesquisa);
        if (resultados.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(resultados);
        }
    }

    public ResponseEntity<?> realizarAvaliacao(String email, Long codigo, int qtdEstrelas, String descricao) {
        Optional<UsuarioModel> usuarioOptional = usuarioRepository.findByEmail(email);

        Optional<LivroModel> livroOptional = livroRepository.findById(codigo);

        if (usuarioOptional.isPresent() && livroOptional.isPresent()) {

            boolean validarCodigo = livroPedidoRepository.existsByLivroAndUsuario(livroOptional.get().getCodigo(),
                    usuarioOptional.get().getCodigo());

            if (validarCodigo) {
                AvaliacaoModel avaliacaoExistente = avaliacaoRepository.findByUsuarioAndLivro(usuarioOptional.get(),
                        livroOptional.get());

                if (avaliacaoExistente != null) {
                    avaliacaoExistente.setQtdEstrelas(qtdEstrelas);
                    avaliacaoExistente.setDescricao(descricao);
                    avaliacaoRepository.save(avaliacaoExistente);
                    return ResponseEntity.ok("Avaliação alterada!");
                } else {
                    AvaliacaoModel novaAvaliacao = new AvaliacaoModel();
                    novaAvaliacao.setUsuario(usuarioOptional.get());
                    novaAvaliacao.setLivro(livroOptional.get());
                    novaAvaliacao.setQtdEstrelas(qtdEstrelas);
                    novaAvaliacao.setDescricao(descricao);

                    avaliacaoRepository.save(novaAvaliacao);
                    return ResponseEntity.ok("Nova avaliação realizada!");
                }
            } else {
                return ResponseEntity.badRequest().build();
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    public double calcularMediaAvaliacao(Long codigoLivro) {
        return avaliacaoRepository.findMediaAvaliacaoByLivroCodigo(codigoLivro);
    }
}
