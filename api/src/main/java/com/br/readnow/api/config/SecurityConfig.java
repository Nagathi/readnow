package com.br.readnow.api.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import jakarta.servlet.DispatcherType;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    SecurityFilter securityFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(HttpMethod.POST, "/efetua-login").permitAll()
                        .requestMatchers(HttpMethod.POST, "/efetua-cadastro").permitAll()
                        .requestMatchers(HttpMethod.GET, "/login").permitAll()
                        .requestMatchers(HttpMethod.POST, "/envia-email").permitAll()
                        .requestMatchers(HttpMethod.GET, "/redefine-senha/**").permitAll()
                        .requestMatchers(HttpMethod.PUT, "/altera-senha").permitAll()
                        .requestMatchers(HttpMethod.GET, "/esqueci-minha-senha").permitAll()
                        .requestMatchers(HttpMethod.GET, "/cadastrado").permitAll()
                        .requestMatchers(HttpMethod.GET, "/cadastro").permitAll()
                        .requestMatchers(HttpMethod.POST, "/autenticacao").permitAll()
                        .requestMatchers(HttpMethod.GET, "/livros/{categoria}").permitAll()
                        .requestMatchers(HttpMethod.GET, " /busca-livro/{codigo}").permitAll()
                        .requestMatchers(HttpMethod.GET, " /pesquisa").permitAll()
                        .requestMatchers(HttpMethod.GET, "/media-avaliacao/{codigoLivro}").permitAll()
                        .requestMatchers("/").permitAll()
                        .requestMatchers("/alterar-senha/**").permitAll()
                        .requestMatchers("/css/**", "/js/**", "/images/**", "/components/**").permitAll()
                        .requestMatchers(
                                "https://h-apigateway.conectagov.estaleiro.serpro.gov.br/api-cep/v1/consulta/cep/**")
                        .permitAll()
                        .requestMatchers(HttpMethod.POST,
                                "https://h-apigateway.conectagov.estaleiro.serpro.gov.br/oauth2/**")
                        .permitAll()
                        .requestMatchers("/adiciona-livro/**").hasRole("USER")
                        .requestMatchers("/atualiza-carrinho/**").hasRole("USER")
                        .requestMatchers(HttpMethod.GET, "/cartoes/**").hasRole("USER")
                        .requestMatchers(HttpMethod.PUT, "/edita-cartao").hasRole("USER")
                        .requestMatchers(HttpMethod.DELETE, "/exclui-cartao").hasRole("USER")
                        .requestMatchers("/enderecos").hasRole("1")
                        .requestMatchers(HttpMethod.POST, "/cadastra-endereco").hasRole("USER")
                        .requestMatchers(HttpMethod.PUT, "/edita-endereco").hasRole("USER")
                        .requestMatchers(HttpMethod.DELETE, "/exclui-endereco/**").hasRole("USER")
                        .requestMatchers(HttpMethod.GET, "/endereco/**").hasRole("USER")
                        .requestMatchers(HttpMethod.POST, "/avaliacao/").hasRole("USER")
                        .requestMatchers(HttpMethod.GET, "/pedidos").hasRole("USER")
                        .requestMatchers(HttpMethod.POST, "/salva-pedido").hasRole("USER")
                        .requestMatchers(HttpMethod.GET, "/pedidos-usuario/{token}").hasRole("USER")
                        .requestMatchers(HttpMethod.GET, "/pedidos-pendentes/{token}").hasRole("USER")
                        .requestMatchers(HttpMethod.GET, "/pedidos-ajuda/{token}").hasRole("USER")
                        .requestMatchers(HttpMethod.PUT, "/foto").hasRole("USER")
                        .requestMatchers(HttpMethod.GET, "/busca-foto").hasRole("USER")
                        .requestMatchers(HttpMethod.POST, "/cadastra-livro").hasRole("ADMIN")
                        .anyRequest().authenticated())
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
            throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
