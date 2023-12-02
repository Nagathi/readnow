package com.br.readnow.api.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.br.readnow.api.dto.EnderecoDTO;
import com.br.readnow.api.model.EnderecoModel;
import com.br.readnow.api.model.UsuarioModel;
import com.br.readnow.api.repository.EnderecoRepository;
import com.br.readnow.api.repository.UsuarioRepository;

@Service
public class ContaService {

    @Autowired
    private EnderecoRepository enderecoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public ResponseEntity<List<EnderecoDTO>> listarEnderecosPorEmail(String email){
        Optional<UsuarioModel> usuarioOptional = usuarioRepository.findByEmail(email);

        if(usuarioOptional.isPresent()){
            UsuarioModel usuario = usuarioOptional.get();
            List<EnderecoModel> enderecosModel = enderecoRepository.findAllByUsuarioId(usuario.getCodigo());
            List<EnderecoDTO> enderecosDTO = new ArrayList<>();

            for (EnderecoModel enderecoModel : enderecosModel) {
                EnderecoDTO enderecoDTO = new EnderecoDTO();
                enderecoDTO.setNomeDestino(enderecoModel.getNomeDestino());
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


    public ResponseEntity<List<EnderecoDTO>> cadastrarEndereco(EnderecoDTO enderecoDTO){
        Optional<UsuarioModel> usuarioOptional = usuarioRepository.findByEmail(enderecoDTO.getEmail());

        if(usuarioOptional.isPresent()){
            UsuarioModel usuario = usuarioOptional.get();

            EnderecoModel novoEndereco = new EnderecoModel();
            novoEndereco.setNomeDestino(enderecoDTO.getNomeDestino());
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

            return ResponseEntity.ok(usuario.getEnderecos().stream()
                .map(this::mapToEnderecoDTO)
                .collect(Collectors.toList()));
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    private EnderecoDTO mapToEnderecoDTO(EnderecoModel enderecoModel) {
        EnderecoDTO enderecoDTO = new EnderecoDTO();
        enderecoDTO.setEmail(enderecoModel.getUsuario().getEmail());
        enderecoDTO.setNomeDestino(enderecoModel.getNomeDestino());
        enderecoDTO.setLogradouro(enderecoModel.getLogradouro());
        enderecoDTO.setBairro(enderecoModel.getBairro());
        enderecoDTO.setNumeroCasa(enderecoModel.getNumeroCasa());
        enderecoDTO.setCep(enderecoModel.getCep());
        enderecoDTO.setComplemento(enderecoModel.getComplemento());
        enderecoDTO.setCidade(enderecoModel.getCidade());
        enderecoDTO.setEstado(enderecoModel.getEstado());
        enderecoDTO.setPais(enderecoModel.getPais());
        return enderecoDTO;
    }

}
