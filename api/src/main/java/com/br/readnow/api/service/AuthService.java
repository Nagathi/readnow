package com.br.readnow.api.service;

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

    public ResponseEntity<?> verificarUUID(String uuid) {        
        Optional<AuthModel> authModels = authRepository.findByUuid(uuid);
        Optional<UsuarioModel> usuario = usuarioRepository.findByCodigo(authModels.get().getUsuario().getCodigo());

        if (usuario.isPresent()) {
            UsuarioDTO usuarioDTO = new UsuarioDTO();
            usuarioDTO.setNome(usuario.get().getNome());
            usuarioDTO.setEmail(usuario.get().getEmail());
            usuarioDTO.setToken(authModels.get().getUuid());
            usuarioDTO.setTipo(usuario.get().getRole());

            return ResponseEntity.ok(usuarioDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
