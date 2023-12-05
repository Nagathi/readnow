package com.br.readnow.api.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.br.readnow.api.dto.CartaoDTO;
import com.br.readnow.api.dto.EnderecoDTO;
import com.br.readnow.api.model.CartaoModel;
import com.br.readnow.api.model.EnderecoModel;
import com.br.readnow.api.model.UsuarioModel;
import com.br.readnow.api.repository.CartaoRepository;
import com.br.readnow.api.repository.EnderecoRepository;
import com.br.readnow.api.repository.UsuarioRepository;

@Service
public class ContaService {

    @Autowired
    private EnderecoRepository enderecoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private CartaoRepository cartaoRepository;

    public ResponseEntity<List<EnderecoDTO>> listarEnderecosPorEmail(String email){
        Optional<UsuarioModel> usuarioOptional = usuarioRepository.findByEmail(email);

        if(usuarioOptional.isPresent()){
            UsuarioModel usuario = usuarioOptional.get();
            List<EnderecoModel> enderecosModel = enderecoRepository.findAllByUsuarioId(usuario.getCodigo());
            List<EnderecoDTO> enderecosDTO = new ArrayList<>();

            for (EnderecoModel enderecoModel : enderecosModel) {
                EnderecoDTO enderecoDTO = new EnderecoDTO();
                enderecoDTO.setCodigo(enderecoModel.getCodigo());
                enderecoDTO.setNomeDestino(enderecoModel.getNomeDestino());
                enderecoDTO.setTelefone(enderecoModel.getTelefone());
                enderecoDTO.setLogradouro(enderecoModel.getLogradouro());
                enderecoDTO.setBairro(enderecoModel.getBairro());
                enderecoDTO.setNumeroCasa(enderecoModel.getNumeroCasa());
                enderecoDTO.setCep(enderecoModel.getCep());
                enderecoDTO.setComplemento(enderecoModel.getComplemento());
                enderecoDTO.setCidade(enderecoModel.getCidade());
                enderecoDTO.setEstado(enderecoModel.getEstado());
                enderecoDTO.setPais(enderecoModel.getPais());

                enderecosDTO.add(enderecoDTO);
            }

            if (!enderecosDTO.isEmpty()) {
                return ResponseEntity.ok(enderecosDTO);
            } else {
                return ResponseEntity.noContent().build();
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    public ResponseEntity<?> cadastrarEndereco(EnderecoDTO enderecoDTO){
        Optional<UsuarioModel> usuarioOptional = usuarioRepository.findByEmail(enderecoDTO.getEmail());

        if(usuarioOptional.isPresent()){
            UsuarioModel usuario = usuarioOptional.get();

            EnderecoModel novoEndereco = new EnderecoModel();
            novoEndereco.setNomeDestino(enderecoDTO.getNomeDestino());
            novoEndereco.setTelefone(enderecoDTO.getTelefone());
            novoEndereco.setLogradouro(enderecoDTO.getLogradouro());
            novoEndereco.setBairro(enderecoDTO.getBairro());
            novoEndereco.setNumeroCasa(enderecoDTO.getNumeroCasa());
            novoEndereco.setCep(enderecoDTO.getCep());
            novoEndereco.setComplemento(enderecoDTO.getComplemento());
            novoEndereco.setCidade(enderecoDTO.getCidade());
            novoEndereco.setEstado(enderecoDTO.getEstado());
            novoEndereco.setPais(enderecoDTO.getPais());
            novoEndereco.setUsuario(usuario);

            enderecoRepository.save(novoEndereco);

            usuario.getEnderecos().add(novoEndereco);
            usuarioRepository.save(usuario);

            return ResponseEntity.ok("Endereço cadastrado!");
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    public ResponseEntity<?> editarEndereco(EnderecoDTO enderecoDTO){
        Optional<EnderecoModel> enderecoOptional = enderecoRepository.findById(enderecoDTO.getCodigo());

        if(enderecoOptional.isPresent()){
            EnderecoModel endereco = enderecoOptional.get();

            endereco.setNomeDestino(enderecoDTO.getNomeDestino());
            endereco.setTelefone(enderecoDTO.getTelefone());
            endereco.setLogradouro(enderecoDTO.getLogradouro());
            endereco.setBairro(enderecoDTO.getBairro());
            endereco.setNumeroCasa(enderecoDTO.getNumeroCasa());
            endereco.setCep(enderecoDTO.getCep());
            endereco.setComplemento(enderecoDTO.getComplemento());
            endereco.setCidade(enderecoDTO.getCidade());
            endereco.setEstado(enderecoDTO.getEstado());
            endereco.setPais(enderecoDTO.getPais());

            enderecoRepository.save(endereco);

            return ResponseEntity.ok("Endereço atualizado!");
        }else{
            return ResponseEntity.badRequest().body("Ocorreu um erro ao atualizar o endereço.");
        }
    }

    public ResponseEntity<?> excluirEndereco(Long codigo){
        
        if(enderecoRepository.findById(codigo).isPresent()){
            enderecoRepository.deleteById(codigo);
            return ResponseEntity.ok("Endereço excluído");
        }else{
            return ResponseEntity.notFound().build();
        }

    }

    public ResponseEntity<EnderecoDTO> buscarEnderecoPorCodigo(Long codigo){
        Optional<EnderecoModel> enderecoOptional = enderecoRepository.findById(codigo);

        if(enderecoOptional.isPresent()){
            EnderecoModel endereco = enderecoOptional.get();

            EnderecoDTO enderecoDTO = new EnderecoDTO();
            enderecoDTO.setCodigo(endereco.getCodigo());
            enderecoDTO.setNomeDestino(endereco.getNomeDestino());
            enderecoDTO.setTelefone(endereco.getTelefone());
            enderecoDTO.setLogradouro(endereco.getLogradouro());
            enderecoDTO.setBairro(endereco.getBairro());
            enderecoDTO.setNumeroCasa(endereco.getNumeroCasa());
            enderecoDTO.setCep(endereco.getCep());
            enderecoDTO.setComplemento(endereco.getComplemento());
            enderecoDTO.setCidade(endereco.getCidade());
            enderecoDTO.setEstado(endereco.getEstado());
            enderecoDTO.setPais(endereco.getPais());

            return ResponseEntity.ok(enderecoDTO);  
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    public ResponseEntity<?> cadastrarCartao(CartaoDTO cartaoDTO){
        Optional<UsuarioModel> usuarioOptional = usuarioRepository.findByEmail(cartaoDTO.getEmail());
        if(usuarioOptional.isPresent()){
            UsuarioModel usuario = usuarioOptional.get();

            CartaoModel cartao = new CartaoModel();
            cartao.setNome(cartaoDTO.getNome());

            String encryptedNumero = new BCryptPasswordEncoder().encode(cartaoDTO.getNumero());

            cartao.setNumero(encryptedNumero);
            cartao.setData(cartaoDTO.getData());
            cartao.setUsuario(usuario);
            cartaoRepository.save(cartao);

            return ResponseEntity.ok("Cartão cadastrado!");
        }else{
            return ResponseEntity.notFound().build();
        }
        
        
    }

}
