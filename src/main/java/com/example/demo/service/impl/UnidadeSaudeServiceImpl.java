package com.example.demo.service.impl;

import com.example.demo.dto.request.UnidadeSaudeRequest;
import com.example.demo.dto.response.UnidadeSaudeResponse;
import com.example.demo.entity.UnidadeSaude;
import com.example.demo.exception.BusinessException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.mapper.UnidadeSaudeMapper;
import com.example.demo.repository.UnidadeSaudeRepository;
import com.example.demo.service.UnidadeSaudeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UnidadeSaudeServiceImpl implements UnidadeSaudeService {

    private final UnidadeSaudeRepository repository;
    private final UnidadeSaudeMapper mapper;

    @Override
    @Transactional
    public UnidadeSaudeResponse criar(UnidadeSaudeRequest request) {
        log.info("Criando unidade de saúde: {}", request.getNome());

        if (repository.existsByNomeIgnoreCase(request.getNome())) {
            throw new BusinessException("Já existe uma unidade de saúde com o nome: " + request.getNome());
        }

        UnidadeSaude entidade = mapper.toEntity(request);
        UnidadeSaude salvo = repository.save(entidade);

        log.info("Unidade de saúde criada com id: {}", salvo.getId());
        return mapper.toResponse(salvo);
    }

    @Override
    @Transactional(readOnly = true)
    public UnidadeSaudeResponse buscarPorId(Long id) {
        log.info("Buscando unidade de saúde com id: {}", id);
        UnidadeSaude entidade = buscarEntidadePorId(id);
        return mapper.toResponse(entidade);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<UnidadeSaudeResponse> listar(String nome, String tipo, Boolean ativo, Pageable pageable) {
        log.info("Listando unidades — nome: {}, tipo: {}, ativo: {}", nome, tipo, ativo);
        return repository.buscarComFiltros(nome, tipo, ativo, pageable)
                .map(mapper::toResponse);
    }

    @Override
    @Transactional
    public UnidadeSaudeResponse atualizar(Long id, UnidadeSaudeRequest request) {
        log.info("Atualizando unidade de saúde com id: {}", id);

        UnidadeSaude entidade = buscarEntidadePorId(id);

        if (repository.existsByNomeIgnoreCaseAndIdNot(request.getNome(), id)) {
            throw new BusinessException("Já existe outra unidade de saúde com o nome: " + request.getNome());
        }

        mapper.updateEntityFromRequest(request, entidade);
        UnidadeSaude atualizado = repository.save(entidade);

        log.info("Unidade de saúde atualizada com id: {}", atualizado.getId());
        return mapper.toResponse(atualizado);
    }

    @Override
    @Transactional
    public void deletar(Long id) {
        log.info("Deletando unidade de saúde com id: {}", id);
        UnidadeSaude entidade = buscarEntidadePorId(id);
        repository.delete(entidade);
        log.info("Unidade de saúde deletada com id: {}", id);
    }

    @Override
    @Transactional
    public UnidadeSaudeResponse alternarAtivo(Long id) {
        log.info("Alternando status ativo da unidade de saúde com id: {}", id);
        UnidadeSaude entidade = buscarEntidadePorId(id);
        entidade.setAtivo(!entidade.getAtivo());
        return mapper.toResponse(repository.save(entidade));
    }

    // Método privado reutilizável — evita duplicar tratamento de not found
    private UnidadeSaude buscarEntidadePorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Unidade de Saúde", id));
    }
}