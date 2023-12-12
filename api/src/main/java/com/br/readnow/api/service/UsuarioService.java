package com.br.readnow.api.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.br.readnow.api.dto.LoginDTO;
import com.br.readnow.api.dto.PerfilDTO;
import com.br.readnow.api.dto.RedefinirSenhaDTO;
import com.br.readnow.api.dto.RequestNovaSenhaDTO;
import com.br.readnow.api.dto.UsuarioDTO;
import com.br.readnow.api.model.AuthModel;
import com.br.readnow.api.model.CarrinhoModel;
import com.br.readnow.api.model.LinkModel;
import com.br.readnow.api.model.UserRoleModel;
import com.br.readnow.api.model.UsuarioModel;
import com.br.readnow.api.repository.TokenRepository;
import com.br.readnow.api.repository.CarrinhoRepository;
import com.br.readnow.api.repository.LinkRepository;
import com.br.readnow.api.repository.UsuarioRepository;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private TokenRepository authRepository;

    @Autowired
    private LinkRepository linkRepository;

    @Autowired
    private JavaMailSender emailSender;

    @Autowired
    private CarrinhoRepository carrinhoRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    public ResponseEntity<UsuarioDTO> login(LoginDTO login) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(login.email(), login.senha());
        var authentication = this.authenticationManager.authenticate(usernamePassword);

        var token = tokenService.gerarToken((UsuarioModel) authentication.getPrincipal());

        Optional<UsuarioModel> usuarioOptional = usuarioRepository.findByEmail(login.email());

        if (authRepository.existsByUsuario(usuarioOptional.get())) {
            authRepository.expireSession(usuarioOptional.get());
        }

        AuthModel auth = new AuthModel();
        auth.setUsuario(usuarioOptional.get());
        auth.setExpirado(false);
        auth.setUuid(token);
        authRepository.save(auth);

        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setNome(usuarioOptional.get().getNome());
        usuarioDTO.setTipo(usuarioOptional.get().getRole());
        usuarioDTO.setToken(auth.getUuid());
        usuarioDTO.setEmail(login.email());

        return ResponseEntity.ok(usuarioDTO);

    }

    public ResponseEntity<?> cadastrarUsuario(UsuarioModel usuario) {
        if (usuarioRepository.existsByEmail(usuario.getEmail())) {
            return ResponseEntity.badRequest().body("E-mail já cadastrado");
        } else {
            String encryptedPassword = new BCryptPasswordEncoder().encode(usuario.getSenha());

            LoginDTO loginDTO = new LoginDTO(usuario.getEmail(), usuario.getSenha());
            usuario.setSenha(encryptedPassword);
            usuario.setRole(UserRoleModel.ADMIN);
            usuarioRepository.save(usuario);

            CarrinhoModel carrinhoModel = new CarrinhoModel();
            carrinhoModel.criarCarrinho(usuario);
            carrinhoRepository.save(carrinhoModel);

            return login(loginDTO);
        }
    }

    public ResponseEntity<?> enviarEmail(String email) {
        Optional<UsuarioModel> usuarioOptional = usuarioRepository.findByEmail(email);

        if (usuarioOptional.isPresent()) {
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
            message.setText(
                    "Olá! Clique no link a seguir para redefinir sua senha: http://localhost:8080/alterar-senha?link="
                            + link);

            emailSender.send(message);

            return ResponseEntity.ok("E-mail de redefinição enviado com sucesso");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    public ResponseEntity<?> recuperarSenha(String token) {
        Optional<LinkModel> linkOptional = linkRepository.findById(token);

        if (linkOptional.isPresent()) {
            if (!linkExpirado(linkOptional.get())) {
                RedefinirSenhaDTO usuario = new RedefinirSenhaDTO();
                usuario.setEmail(linkOptional.get().getUsuario().getEmail());

                return ResponseEntity.ok(usuario);
            } else {
                return ResponseEntity.badRequest().body("Esse link está expirado.");
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    public ResponseEntity<?> redefinirSenha(RequestNovaSenhaDTO request) {

        Optional<LinkModel> linkOptional = linkRepository.findById(request.getToken());

        if (linkOptional.isPresent() && !linkOptional.get().isExpirado()) {

            Optional<UsuarioModel> usuarioOptional = usuarioRepository
                    .findById(linkOptional.get().getUsuario().getCodigo());

            if (usuarioOptional.isPresent()) {

                UsuarioModel usuario = usuarioOptional.get();
                String encryptedPassword = new BCryptPasswordEncoder().encode(request.getSenha());

                usuario.setSenha(encryptedPassword);

                LinkModel link = linkOptional.get();
                link.setExpirado(true);

                usuarioRepository.save(usuario);
                linkRepository.save(link);

                return ResponseEntity.ok("Senha alterada com sucesso!");
            } else {
                return ResponseEntity.notFound().build();
            }

        } else {
            return ResponseEntity.badRequest().body("Esse link está expirado ou é inválido.");
        }
    }

    public ResponseEntity<?> alterarFoto(MultipartFile foto, String nome, String email) {
        Optional<UsuarioModel> usuarOptional = usuarioRepository.findByEmail(email);

        try {
            if (usuarOptional.isPresent()) {
                String uploadImagem = "C:/Users/gu-gu/OneDrive/Documentos/Eng. de Computação/Desenvolvimento Web/Trabalho 1/API/api/src/main/resources/static/images/usuarios/";
                String uniqueImageName = UUID.randomUUID().toString() + "_" + foto.getOriginalFilename();

                Path destinoImagem = Path.of(uploadImagem + uniqueImageName);
                Files.copy(foto.getInputStream(), destinoImagem, StandardCopyOption.REPLACE_EXISTING);

                UsuarioModel usuario = usuarOptional.get();
                usuario.setFoto(uniqueImageName);
                usuario.setNome(nome);
                usuarioRepository.save(usuario);

                return ResponseEntity.ok("Foto alterada!");
            } else {
                return ResponseEntity.notFound().build();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return ResponseEntity.badRequest().build();

    }

    public ResponseEntity<PerfilDTO> retornarNomeEFoto(String email) {
        Optional<UsuarioModel> usuarioOptional = usuarioRepository.findByEmail(email);
        if (usuarioOptional.isPresent()) {
            PerfilDTO perfilDTO = new PerfilDTO();
            perfilDTO.setFoto(usuarioOptional.get().getFoto());
            perfilDTO.setNome(usuarioOptional.get().getNome());
            return ResponseEntity.ok(perfilDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    public boolean linkExpirado(LinkModel link) {
        LocalDateTime criadoEm = link.getCriadoEm();
        LocalDateTime agora = LocalDateTime.now();

        return agora.isAfter(criadoEm.plus(15, ChronoUnit.MINUTES));
    }

    public ResponseEntity<String> logout(String token) {
        String email = tokenService.validarToken(token);
        Optional<UsuarioModel> usuarioOptional = usuarioRepository.findByEmail(email);
        if (authRepository.existsByUsuario(usuarioOptional.get())) {
            authRepository.expireSession(usuarioOptional.get());
        }
        return ResponseEntity.ok("Logout realizado com sucesso");
    }
}
