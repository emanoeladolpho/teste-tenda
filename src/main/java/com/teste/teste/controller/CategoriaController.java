package com.teste.teste.controller;

import com.teste.teste.dto.request.CategoriaRequestDto;
import com.teste.teste.dto.response.CategoriaResponseDto;
import com.teste.teste.service.CategoriaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/categorias")
@RestController
public class CategoriaController {

    private final CategoriaService categoriaService;

    public CategoriaController(CategoriaService categoriaService) {
        this.categoriaService = categoriaService;
    }

    @GetMapping
    public ResponseEntity<List<CategoriaResponseDto>> listarCategoriasAtivas(){
        return ResponseEntity.ok(categoriaService.listarCategoriasAtivas());
    }

    @GetMapping("/inativas")
    public ResponseEntity<List<CategoriaResponseDto>> listarCategoriasInativas(){
        return ResponseEntity.ok(categoriaService.listarCategoriasInativas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoriaResponseDto> buscarPorId(@PathVariable("id") Long idCategoria){
        return ResponseEntity.ok(categoriaService.buscarPorId(idCategoria));
    }

    @PostMapping
    public ResponseEntity<CategoriaResponseDto> cadastrar(@RequestBody CategoriaRequestDto categoria){
        return ResponseEntity.status(HttpStatus.CREATED).body(categoriaService.cadastrarCategoria(categoria));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoriaResponseDto> atualizar(
            @PathVariable("id") Long idCategoria,
            @RequestBody CategoriaRequestDto categoria){
        return ResponseEntity.ok(categoriaService.atualizarCategoria(idCategoria, categoria));
    }

    @PutMapping("/{id}/desativar")
    public ResponseEntity<Void> desativar(@PathVariable("id") Long idCategoria){
        categoriaService.desativarCategoria(idCategoria);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PutMapping("/{id}/ativar")
    public ResponseEntity<CategoriaResponseDto> ativar(@PathVariable("id") Long idCategoria){
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(categoriaService.ativarCategoria(idCategoria));
    }
}