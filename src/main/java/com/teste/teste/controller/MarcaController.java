package com.teste.teste.controller;

import com.teste.teste.dto.request.MarcaRequestDto;
import com.teste.teste.dto.response.MarcaResponseDto;
import com.teste.teste.service.MarcaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/marcas")
@RestController
public class MarcaController {

    private final MarcaService marcaService;

    public MarcaController(MarcaService marcaService) {
        this.marcaService = marcaService;
    }

    @GetMapping
    public ResponseEntity<List<MarcaResponseDto>> listarMarcasAtivas(){
        return ResponseEntity.ok(marcaService.listarMarcasAtivas());
    }

    @GetMapping("/inativas")
    public ResponseEntity<List<MarcaResponseDto>> listarMarcasInativas(){
        return ResponseEntity.ok(marcaService.listarMarcasInativas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MarcaResponseDto> buscarPorId(@PathVariable("id") Long idMarca){
        return ResponseEntity.ok(marcaService.buscarPorId(idMarca));
    }

    @PostMapping
    public ResponseEntity<MarcaResponseDto> cadastrar(@RequestBody MarcaRequestDto marca){
        return ResponseEntity.status(HttpStatus.CREATED).body(marcaService.cadastrarMarca(marca));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MarcaResponseDto> atualizar(
            @PathVariable("id") Long idMarca,
            @RequestBody MarcaRequestDto marca){
        return ResponseEntity.ok(marcaService.atualizarMarca(idMarca,marca));
    }

    @PutMapping("/{id}/desativar")
    public ResponseEntity<Void> desativar(@PathVariable("id") Long idMarca){
        marcaService.desativarMarca(idMarca);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PutMapping("/{id}/ativar")
    public ResponseEntity<MarcaResponseDto> ativar(@PathVariable("id") Long idMarca){
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(marcaService.ativarMarca(idMarca));
    }
}