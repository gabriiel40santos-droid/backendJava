package com.example.demo.controller;

import com.example.demo.dto.request.UnidadeSaudeRequest;
import com.example.demo.dto.response.UnidadeSaudeResponse;
import com.example.demo.service.UnidadeSaudeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@Slf4j
@RestController
@RequestMapping("/api/v1/unidades-saude")
@RequiredArgsConstructor
public class UnidadeSaudeController {

    private final UnidadeSaudeService service;

    // POST /api/v1/unidades-saude
    @PostMapping
    public ResponseEntity<UnidadeSaudeResponse> criar(@Valid @RequestBody UnidadeSaudeRequest request) {
        UnidadeSaudeResponse response = service.criar(request);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(response.getId())
                .toUri();
        return ResponseEntity.created(location).body(response);
    }

    // GET /api/v1/unidades-saude/{id}
    @GetMapping("/{id}")
    public ResponseEntity<UnidadeSaudeResponse> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    // GET /api/v1/unidades-saude?nome=&tipo=&ativo=&page=0&size=10&sort=nome,asc
    @GetMapping
    public ResponseEntity<Page<UnidadeSaudeResponse>> listar(
            @RequestParam(required = false) String nome,
            @RequestParam(required = false) String tipo,
            @RequestParam(required = false) Boolean ativo,
            @PageableDefault(size = 10, sort = "nome", direction = Sort.Direction.ASC) Pageable pageable
    ) {
        return ResponseEntity.ok(service.listar(nome, tipo, ativo, pageable));
    }

    // PUT /api/v1/unidades-saude/{id}
    @PutMapping("/{id}")
    public ResponseEntity<UnidadeSaudeResponse> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody UnidadeSaudeRequest request
    ) {
        return ResponseEntity.ok(service.atualizar(id, request));
    }

    // DELETE /api/v1/unidades-saude/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }

    // PATCH /api/v1/unidades-saude/{id}/alternar-ativo
    @PatchMapping("/{id}/alternar-ativo")
    public ResponseEntity<UnidadeSaudeResponse> alternarAtivo(@PathVariable Long id) {
        return ResponseEntity.ok(service.alternarAtivo(id));
    }
}