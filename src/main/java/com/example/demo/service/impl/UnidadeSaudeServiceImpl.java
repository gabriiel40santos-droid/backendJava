package com.example.demo.service.impl;

import com.example.demo.dto.request.UnidadeSaudeRequest;
import com.example.demo.dto.response.UnidadeSaudeResponse;
import com.example.demo.entity.UnidadeSaude;
import com.example.demo.exception.BusinessException;
import com.example.demo.repository.UnidadeSaudeRepository;
import com.example.demo.service.UnidadeSaudeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UnidadeSaudeServiceImpl implements UnidadeSaudeService {

    private final UnidadeSaudeRepository repository;

    @Override
    @Transactional
    public UnidadeSaudeResponse criar(UnidadeSaudeRequest request) {
        UnidadeSaude unidade = new UnidadeSaude();
        unidade.setNome(request.getNome());
        unidade.setTipo(request.getTipo());
        unidade.setAtivo(true); // Nova unidade nasce ativa por padrão

        UnidadeSaude salva = repository.save(unidade);
        return converteParaResponse(salva);
    }

    @Override
    @Transactional(readOnly = true)
    public UnidadeSaudeResponse buscarPorId(Long id) {
        UnidadeSaude unidade = repository.findById(id)
                .orElseThrow(() -> new BusinessException("Unidade de Saude nao encontrada com o ID: " + id));
        return converteParaResponse(unidade);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<UnidadeSaudeResponse> listar(String nome, String tipo, Boolean ativo, Pageable pageable) {
        // Busca paginada simples do JPA. Se quiser filtrar por nome/tipo depois, pode evoluir para Specification ou query methods.
        Page<UnidadeSaude> unidades = repository.findAll(pageable);
        return unidades.map(this::converteParaResponse);
    }

    @Override
    @Transactional
    public UnidadeSaudeResponse atualizar(Long id, UnidadeSaudeRequest request) {
        UnidadeSaude unidade = repository.findById(id)
                .orElseThrow(() -> new BusinessException("Unidade de Saude nao encontrada com o ID: " + id));
        
        unidade.setNome(request.getNome());
        unidade.setTipo(request.getTipo());
        
        UnidadeSaude atualizada = repository.save(unidade);
        return converteParaResponse(atualizada);
    }

    @Override
    @Transactional
    public void deletar(Long id) {
        if (!repository.existsById(id)) {
            throw new BusinessException("Unidade de Saude nao encontrada com o ID: " + id);
        }
        repository.deleteById(id);
    }

    @Override
    @Transactional
    public UnidadeSaudeResponse alternarAtivo(Long id) {
        UnidadeSaude unidade = repository.findById(id)
                .orElseThrow(() -> new BusinessException("Unidade de Saude nao encontrada com o ID: " + id));
        
        unidade.setAtivo(!unidade.getAtivo()); // Inverte o estado atual
        UnidadeSaude atualizada = repository.save(unidade);
        return converteParaResponse(atualizada);
    }

    // Método auxiliar para transformar a Entidade do Banco no DTO de Retorno da API
    private UnidadeSaudeResponse converteParaResponse(UnidadeSaude unidade) {
        UnidadeSaudeResponse response = new UnidadeSaudeResponse();
        response.setId(unidade.getId());
        response.setNome(unidade.getNome());
        response.setTipo(unidade.getTipo());
        response.setAtivo(unidade.getAtivo());
        return response;
    }
}