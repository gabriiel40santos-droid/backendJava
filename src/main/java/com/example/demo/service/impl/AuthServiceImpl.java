package com.example.demo.service.impl;

import com.example.demo.dto.request.LoginRequest;
import com.example.demo.dto.request.RegisterRequest;
import com.example.demo.dto.response.LoginResponse;
import com.example.demo.entity.Usuario;
import com.example.demo.exception.BusinessException;
import com.example.demo.repository.UsuarioRepository;
import com.example.demo.service.AuthService;
import com.example.demo.service.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UsuarioRepository repository;
    private final JwtService jwtService;
    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void register(RegisterRequest request) {

        log.info("Cadastrando usuário: {}", request.getEmail());

        if (repository.findByEmail(request.getEmail()).isPresent()) {
            throw new BusinessException(
                    "Já existe um usuário com o email: "
                            + request.getEmail()
            );
        }

        Usuario usuario = Usuario.builder()
                .nome(request.getNome())
                .email(request.getEmail())
                .senha(passwordEncoder.encode(request.getSenha()))
                .role("USER")
                .build();

        repository.save(usuario);

        log.info("Usuário cadastrado com sucesso");
    }

    @Override
    @Transactional(readOnly = true)
    public LoginResponse login(LoginRequest request) {

        log.info("Tentativa de login: {}", request.getEmail());

        Usuario usuario = repository.findByEmail(request.getEmail())
                .orElseThrow(() ->
                        new BusinessException("Email ou senha inválidos"));

        boolean senhaCorreta =
                passwordEncoder.matches(
                        request.getSenha(),
                        usuario.getSenha()
                );

        if (!senhaCorreta) {
            throw new BusinessException("Email ou senha inválidos");
        }

        String token =
                jwtService.gerarToken(usuario.getEmail());

        log.info("Login realizado com sucesso: {}", usuario.getEmail());

        return LoginResponse.builder()
                .token(token)
                .build();
    }
}
