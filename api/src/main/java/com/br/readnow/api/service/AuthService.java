package com.br.readnow.api.service;

import java.util.Collections;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.br.readnow.api.dto.UsuarioDTO;
import com.br.readnow.api.model.AuthModel;
import com.br.readnow.api.model.UsuarioModel;
import com.br.readnow.api.repository.AuthRepository;
import com.br.readnow.api.repository.UsuarioRepository;

@Service
public class AuthService {
    
    @Autowired
    private AuthRepository authRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public ResponseEntity<UsuarioDTO> verificarUUID(String id) {
        Iterable<AuthModel> authModels = authRepository.findAllById(Collections.singletonList(id));
    
        for (AuthModel authModel : authModels) {
            if (!authModel.isExpirado()) {
                Optional<UsuarioModel> usuario = usuarioRepository.findById(authModel.getUsuario().getCodigo());
    
                if (usuario.isPresent()) {
                    UsuarioDTO usuarioDTO = new UsuarioDTO();
                    usuarioDTO.setNome(usuario.get().getNome());
                    usuarioDTO.setId(authModel.getUuid());
                    usuarioDTO.setTipo(usuario.get().getTipo());
    
                    return ResponseEntity.ok(usuarioDTO);
                } else {
                    return ResponseEntity.notFound().build();
                }
            }
        }
    
        return ResponseEntity.notFound().build();
    }

}
