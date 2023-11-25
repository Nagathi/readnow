package com.br.readnow.api.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.br.readnow.api.dto.LivroDTO;
import com.br.readnow.api.model.LivroModel;
import com.br.readnow.api.repository.LivroRepository;

@Service
public class LivroService {
    
    @Autowired
    private LivroRepository livroRepository;

    public Iterable<LivroModel> listarLivros(){
        return livroRepository.findAll();
    }

    public List<LivroModel> listarLivrosPorCategoria(String categoria) {
        return livroRepository.findByCategoria(categoria);
    }

    public ResponseEntity<?> cadastrarLivro(LivroDTO livroDTO){

        LivroModel livro = new LivroModel();


        try{
            if(!livroRepository.existsByIsbn(livroDTO.getIsbn())){
                MultipartFile imagem = livroDTO.getImagem();

                //  C:/Users/gu-gu/OneDrive/Documentos/Eng. de Computação/Desenvolvimento Web/Trabalho 1/API/api/src/main/resources/static/images/livros/
                //  /home/victoria/Documentos/UFPA/Optativas/DesenvolvimentoWeb/ReadNow/api/src/main/resources/static/images/livros/
                String uploadImagem = "/home/victoria/Documentos/UFPA/Optativas/DesenvolvimentoWeb/ReadNow/api/src/main/resources/static/images/livros/";
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
                livro.setPreco(livroDTO.getPreco());

                livroRepository.save(livro);
                return ResponseEntity.status(HttpStatus.CREATED).body("Livro cadastrado!");
            }else{
                return ResponseEntity.badRequest().body("Esse ISBN já existe em nosso banco de dados!");
            }

        }catch(IOException e){
            e.printStackTrace();
        }

        return ResponseEntity.badRequest().build();
    }

    public ResponseEntity<?> buscarLivro(long codigo){
         
        if(livroRepository.findById(codigo).isPresent()){
            LivroModel livro = livroRepository.findById(codigo).get();
            return ResponseEntity.ok(livro);
        }else{
            return ResponseEntity.notFound().build();
        }
    }
}
