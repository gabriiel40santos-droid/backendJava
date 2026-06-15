package com.example.demo.service;

import com.example.demo.dto.request.UnidadeSaudeRequest;
import com.example.demo.dto.response.UnidadeSaudeResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UnidadeSaudeService {
    UnidadeSaudeResponse criar(UnidadeSaudeRequest request);
    UnidadeSaudeResponse buscarPorId(Long id);
    Page<UnidadeSaudeResponse> listar(String nome, String tipo, Boolean ativo, Pageable pageable);
    UnidadeSaudeResponse atualizar(Long id, UnidadeSaudeRequest request);
    void deletar(Long id);
    UnidadeSaudeResponse alternarAtivo(Long id);
}