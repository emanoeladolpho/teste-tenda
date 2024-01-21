package com.teste.teste.controller;

import com.teste.teste.dto.request.ProdutoRequestDto;
import com.teste.teste.dto.response.ProdutoResponseDto;
import com.teste.teste.service.ProdutoService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequestMapping("/produtos")
@RestController
public class ProdutoController {

    private final ProdutoService produtoService;

    public ProdutoController(ProdutoService produtoService) {
        this.produtoService = produtoService;
    }

    @GetMapping
    public ResponseEntity<Page<ProdutoResponseDto>> listarAtivos(Pageable pageable){
        return ResponseEntity.ok(produtoService.listarAtivos(pageable));
    }

    @GetMapping("/inativos")
    public ResponseEntity<Page<ProdutoResponseDto>> listarInativo(Pageable pageable){
        return ResponseEntity.ok(produtoService.listarInativos(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProdutoResponseDto> buscarPorId(@PathVariable("id") Long idProduto){
        return ResponseEntity.ok(produtoService.buscarById(idProduto));
    }

    @GetMapping("/categoria/{id}")
    public ResponseEntity<Page<ProdutoResponseDto>> buscarPorCategoria(
            @PathVariable("id") Long idCategoria, Pageable pageable){
        return ResponseEntity.ok(produtoService.buscarPorCategoria(idCategoria, pageable));
    }

    @GetMapping("/marca/{id}")
    public ResponseEntity<Page<ProdutoResponseDto>> buscarPorMarca(
            @PathVariable("id") Long idMarca, Pageable pageable){
        return ResponseEntity.ok(produtoService.buscarPorMarca(idMarca, pageable));
    }

    @GetMapping("/sku/{sku}")
    public ResponseEntity<ProdutoResponseDto> buscarPorSku(@PathVariable("sku") String sku){
        return ResponseEntity.ok(produtoService.buscarPorSku(sku));
    }

    @PostMapping
    public ResponseEntity<ProdutoResponseDto> cadastrar(@Valid @RequestBody ProdutoRequestDto produto){
        return ResponseEntity.status(HttpStatus.CREATED).body(produtoService.cadastrar(produto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProdutoResponseDto> atualizar(
            @PathVariable("id") Long idProduto,
            @Valid @RequestBody ProdutoRequestDto produto){
        return ResponseEntity.status(HttpStatus.CREATED).body(produtoService.atualizarProduto(idProduto,produto));
    }

    @PutMapping("/{id}/desativar")
    public ResponseEntity<Void> desativarProduto(@PathVariable("id") Long idProduto){
        produtoService.desativarProduto(idProduto);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PutMapping("/{id}/ativar")
    public ResponseEntity<ProdutoResponseDto> ativarProduto(@PathVariable("id") Long idProduto){
        return ResponseEntity.ok(produtoService.ativarProduto(idProduto));
    }
}