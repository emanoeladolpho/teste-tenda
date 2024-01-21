package com.teste.teste.service;

import com.teste.teste.dto.request.CategoriaRequestDto;
import com.teste.teste.dto.response.CategoriaResponseDto;
import com.teste.teste.exceptions.ConflitoException;
import com.teste.teste.exceptions.NotFound;
import com.teste.teste.model.Categoria;
import com.teste.teste.repository.CategoriaRepository;
import com.teste.teste.util.MensagensPadrao;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;

    private final ModelMapper modelMapper = new ModelMapper();

    public CategoriaService(CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    public List<CategoriaResponseDto> listarCategoriasAtivas() {
        List<Categoria> categorias = categoriaRepository.findAllByAtivoIsTrue();
        return converterCategoriasParaResponseDto(categorias);
    }

    public List<CategoriaResponseDto> listarCategoriasInativas(){
        List<Categoria> categorias = categoriaRepository.findAllByAtivoIsFalse();
        return converterCategoriasParaResponseDto(categorias);
    }

    public CategoriaResponseDto buscarPorId(Long idCategoria){
        Categoria categoria = categoriaRepository.findById(idCategoria)
                .orElseThrow(new NotFound(MensagensPadrao.CATEGORIA_NOT_FOUND));

        return modelMapper.map(categoria,CategoriaResponseDto.class);
    }

    @Transactional
    public CategoriaResponseDto cadastrarCategoria(CategoriaRequestDto categoriaRequestDto){
        validarCadastroCategoria(categoriaRequestDto);
        Categoria categoria = modelMapper.map(categoriaRequestDto,Categoria.class);
        Categoria categoriaCadastrada = categoriaRepository.save(categoria);
        return modelMapper.map(categoriaCadastrada,CategoriaResponseDto.class);
    }

    private void validarCadastroCategoria(CategoriaRequestDto categoriaRequestDto){
        Categoria categoria = categoriaRepository.findByDescricaoIgnoreCase(categoriaRequestDto.getDescricao());

        if(Objects.nonNull(categoria))
            throw new ConflitoException(MensagensPadrao.CATEGORIA_JA_CADASTRADA);
    }

    @Transactional
    public CategoriaResponseDto atualizarCategoria(Long idCategoria, CategoriaRequestDto categoriaRequestDto){
        validarAtualizacaoCategoria(idCategoria,categoriaRequestDto);
        categoriaRequestDto.setId(idCategoria);
        Categoria categoria = modelMapper.map(categoriaRequestDto,Categoria.class);
        Categoria categoriaCadastrada = categoriaRepository.save(categoria);
        return modelMapper.map(categoriaCadastrada,CategoriaResponseDto.class);
    }

    private void validarAtualizacaoCategoria(Long idCategoria, CategoriaRequestDto categoriaRequestDto){
        Categoria categoria = categoriaRepository
                .findByDescricaoAndIdDifferent(categoriaRequestDto.getDescricao(), idCategoria);

        if(Objects.nonNull(categoria))
            throw new ConflitoException(MensagensPadrao.CATEGORIA_JA_CADASTRADA);
    }

    @Transactional
    public void desativarCategoria(Long idCategoria){
        Categoria categoria = categoriaRepository.findById(idCategoria)
                .orElseThrow(new NotFound(MensagensPadrao.CATEGORIA_NOT_FOUND));

        categoria.desativar();
        categoriaRepository.save(categoria);
    }

    @Transactional
    public CategoriaResponseDto ativarCategoria(Long idCategoria){
        Categoria categoria = categoriaRepository.findById(idCategoria)
                .orElseThrow(new NotFound(MensagensPadrao.CATEGORIA_NOT_FOUND));

        categoria.ativar();
        categoria = categoriaRepository.save(categoria);
        return modelMapper.map(categoria, CategoriaResponseDto.class);
    }

    private List<CategoriaResponseDto> converterCategoriasParaResponseDto(List<Categoria> categorias){
        return categorias.stream()
                .map(categoria -> modelMapper.map(categoria,CategoriaResponseDto.class))
                .collect(Collectors.toList());
    }
}