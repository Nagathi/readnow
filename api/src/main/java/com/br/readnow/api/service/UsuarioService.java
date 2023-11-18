package com.br.readnow.api.service;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.br.readnow.api.dto.LoginDTO;
import com.br.readnow.api.dto.UsuarioDTO;
import com.br.readnow.api.model.AuthModel;
import com.br.readnow.api.model.UsuarioModel;
import com.br.readnow.api.repository.AuthRepository;
import com.br.readnow.api.repository.UsuarioRepository;

@Service
public class UsuarioService {
    
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private AuthRepository authRepository;

    public ResponseEntity<UsuarioDTO> login(LoginDTO login){
        Optional<UsuarioModel> usuarioOptional = usuarioRepository.findByEmailAndSenha(login.getEmail(), login.getSenha());

        if(usuarioOptional.isEmpty()){
            return ResponseEntity.notFound().build();
        }else{

            if(authRepository.existsByUsuario(usuarioOptional.get())){
                authRepository.expireSession(usuarioOptional.get());
            }

            AuthModel auth = new AuthModel();
            auth.setUsuario(usuarioOptional.get());
            auth.setExpirado(false);
            auth.setUuid(UUID.randomUUID().toString());
            authRepository.save(auth);

            UsuarioDTO usuarioDTO = new UsuarioDTO();
            usuarioDTO.setNome(usuarioOptional.get().getNome());
            usuarioDTO.setId(auth.getUuid());

            return ResponseEntity.ok(usuarioDTO);
        }
    }

    public ResponseEntity<?> cadastrarUsuario(UsuarioModel usuario){
        if (usuarioRepository.existsByEmail(usuario.getEmail())) {
            return ResponseEntity.badRequest().body("E-mail já cadastrado");
        } else if (usuarioRepository.existsByUsuario(usuario.getUsuario())) {
            return ResponseEntity.badRequest().body("Usuário já cadastrado");
        } else {
            usuario.setTipo("cliente");
            UsuarioModel novoUsuario = usuarioRepository.save(usuario);
            return ResponseEntity.status(HttpStatus.CREATED).body(novoUsuario);
        }
    }
}
