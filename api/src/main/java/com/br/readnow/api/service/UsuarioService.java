package com.br.readnow.api.service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.br.readnow.api.dto.LoginDTO;
import com.br.readnow.api.dto.RedefinirSenhaDTO;
import com.br.readnow.api.dto.RequestNovaSenhaDTO;
import com.br.readnow.api.dto.UsuarioDTO;
import com.br.readnow.api.model.AuthModel;
import com.br.readnow.api.model.CarrinhoModel;
import com.br.readnow.api.model.LinkModel;
import com.br.readnow.api.model.UsuarioModel;
import com.br.readnow.api.repository.AuthRepository;
import com.br.readnow.api.repository.CarrinhoRepository;
import com.br.readnow.api.repository.LinkRepository;
import com.br.readnow.api.repository.UsuarioRepository;

@Service
public class UsuarioService {
    
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private AuthRepository authRepository;

    @Autowired
    private LinkRepository linkRepository;

    @Autowired
    private JavaMailSender emailSender;

    @Autowired
    private CarrinhoRepository carrinhoRepository;

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
            usuarioDTO.setTipo(usuarioOptional.get().getTipo());
            
            return ResponseEntity.ok(usuarioDTO);

        }
    }

    public ResponseEntity<?> cadastrarUsuario(UsuarioModel usuario){
        if (usuarioRepository.existsByEmail(usuario.getEmail())) {
            return ResponseEntity.badRequest().body("E-mail já cadastrado");
        } else {
            usuario.setTipo("cliente");
            usuarioRepository.save(usuario);

            AuthModel auth = new AuthModel();
            auth.setUsuario(usuario);
            auth.setExpirado(false);
            auth.setUuid(UUID.randomUUID().toString());
            authRepository.save(auth);

            UsuarioDTO usuarioDTO = new UsuarioDTO();
            usuarioDTO.setNome(usuario.getNome());
            usuarioDTO.setId(auth.getUuid());
            usuarioDTO.setTipo(usuario.getTipo());

            CarrinhoModel carrinhoModel = new CarrinhoModel();
            carrinhoModel.criarCarrinho(usuario);
            carrinhoRepository.save(carrinhoModel);

            return ResponseEntity.status(HttpStatus.CREATED).body(usuarioDTO);
        }
    }

    public ResponseEntity<?> enviarEmail(String email){
        Optional<UsuarioModel> usuarioOptional = usuarioRepository.findByEmail(email);

        if(usuarioOptional.isPresent()){
            String link = UUID.randomUUID().toString();

            LinkModel novoLink = new LinkModel();
            
            novoLink.setLink(link);
            novoLink.setUsuario(usuarioOptional.get());

            LocalDateTime expiracao = LocalDateTime.now().plus(15, ChronoUnit.MINUTES);
            novoLink.setCriadoEm(expiracao);

            linkRepository.save(novoLink);

            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(email);
            message.setSubject("Redefinição de Senha");
            message.setText("Olá! Clique no link a seguir para redefinir sua senha: http://localhost:8080/redefineSenha/" + link);

            emailSender.send(message);

            return ResponseEntity.ok("E-mail de redefinição enviado com sucesso");
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    public ResponseEntity<?> recuperarSenha(String token){
        Optional<LinkModel> linkOptional = linkRepository.findById(token);

        if(linkOptional.isPresent()){
            if(!linkExpirado(linkOptional.get())){
                RedefinirSenhaDTO usuario = new RedefinirSenhaDTO();
                usuario.setEmail(linkOptional.get().getUsuario().getEmail());

                return ResponseEntity.ok(usuario);
            }else{
                return ResponseEntity.badRequest().body("Esse link está expirado.");
            }
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    public ResponseEntity<?> redefinirSenha(RequestNovaSenhaDTO request){
        Optional<UsuarioModel> usuarioOptional = usuarioRepository.findByEmail(request.getEmail());

        if(usuarioOptional.isPresent()){
            UsuarioModel usuario = usuarioOptional.get();

            usuario.setSenha(request.getSenha());

            usuarioRepository.save(usuario);

            return ResponseEntity.ok("Senha redefinida com sucesso.");
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    public boolean linkExpirado(LinkModel link) {
        LocalDateTime criadoEm = link.getCriadoEm();
        LocalDateTime agora = LocalDateTime.now();
    
        return agora.isAfter(criadoEm.plus(15, ChronoUnit.MINUTES));
    }
}
