package com.example.demo.mapper;

import com.example.demo.dto.request.UnidadeSaudeRequest;
import com.example.demo.dto.response.UnidadeSaudeResponse;
import com.example.demo.entity.UnidadeSaude;
import org.springframework.stereotype.Component;

@Component
public class UnidadeSaudeMapper {

    public UnidadeSaude toEntity(UnidadeSaudeRequest request) {
        return UnidadeSaude.builder()
                .nome(request.getNome())
                .endereco(request.getEndereco())
                .tipo(request.getTipo())
                .telefone(request.getTelefone())
                .email(request.getEmail())
                .ativo(request.getAtivo() != null ? request.getAtivo() : true)
                .build();
    }

    public UnidadeSaudeResponse toResponse(UnidadeSaude entity) {
        return UnidadeSaudeResponse.builder()
                .id(entity.getId())
                .nome(entity.getNome())
                .endereco(entity.getEndereco())
                .tipo(entity.getTipo())
                .telefone(entity.getTelefone())
                .email(entity.getEmail())
                .ativo(entity.getAtivo())
                .criadoEm(entity.getCriadoEm())
                .atualizadoEm(entity.getAtualizadoEm())
                .build();
    }

    public void updateEntityFromRequest(UnidadeSaudeRequest request, UnidadeSaude entity) {
        entity.setNome(request.getNome());
        entity.setEndereco(request.getEndereco());
        entity.setTipo(request.getTipo());
        entity.setTelefone(request.getTelefone());
        entity.setEmail(request.getEmail());
        if (request.getAtivo() != null) {
            entity.setAtivo(request.getAtivo());
        }
    }
}