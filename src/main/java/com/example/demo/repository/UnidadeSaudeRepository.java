package com.example.demo.repository;

import com.example.demo.entity.UnidadeSaude;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UnidadeSaudeRepository extends JpaRepository<UnidadeSaude, Long> {

    // Busca paginada com filtros opcionais (qualquer campo pode ser nulo = ignorado)
    @Query("""
            SELECT u FROM UnidadeSaude u
            WHERE (:nome IS NULL OR LOWER(u.nome) LIKE LOWER(CONCAT('%', :nome, '%')))
              AND (:tipo IS NULL OR LOWER(u.tipo) = LOWER(:tipo))
              AND (:ativo IS NULL OR u.ativo = :ativo)
            """)
    Page<UnidadeSaude> buscarComFiltros(
            @Param("nome") String nome,
            @Param("tipo") String tipo,
            @Param("ativo") Boolean ativo,
            Pageable pageable
    );

    // Verifica duplicidade de nome (útil para regra de negócio)
    boolean existsByNomeIgnoreCase(String nome);

    // Verifica duplicidade excluindo o próprio registro (para update)
    boolean existsByNomeIgnoreCaseAndIdNot(String nome, Long id);
}