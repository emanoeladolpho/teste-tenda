package com.teste.teste.service;

import com.teste.teste.dto.request.MarcaRequestDto;
import com.teste.teste.dto.response.MarcaResponseDto;
import com.teste.teste.exceptions.ConflitoException;
import com.teste.teste.exceptions.NotFound;
import com.teste.teste.model.Marca;
import com.teste.teste.repository.MarcaRepository;
import com.teste.teste.util.MensagensPadrao;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class MarcaService {

    private final MarcaRepository marcaRepository;

    private final ModelMapper modelMapper = new ModelMapper();

    public MarcaService(MarcaRepository marcaRepository) {
        this.marcaRepository = marcaRepository;
    }

    public List<MarcaResponseDto> listarMarcasAtivas() {
        List<Marca> marcas = marcaRepository.findAllByAtivoIsTrue();
        return converterMarcasParaResponseDto(marcas);
    }

    public List<MarcaResponseDto> listarMarcasInativas(){
        List<Marca> marcas = marcaRepository.findAllByAtivoIsFalse();
        return converterMarcasParaResponseDto(marcas);
    }

    public MarcaResponseDto buscarPorId(Long idMarca){
        Marca marca = marcaRepository.findById(idMarca)
                .orElseThrow(new NotFound(MensagensPadrao.MARCA_NOT_FOUND));

        return modelMapper.map(marca,MarcaResponseDto.class);
    }

    @Transactional
    public MarcaResponseDto cadastrarMarca(MarcaRequestDto marcaRequestDto){
        validarCadastroMarca(marcaRequestDto);
        Marca marca = modelMapper.map(marcaRequestDto,Marca.class);
        Marca marcaCadastrada = marcaRepository.save(marca);
        return modelMapper.map(marcaCadastrada,MarcaResponseDto.class);
    }

    private void validarCadastroMarca(MarcaRequestDto marcaRequestDto){
        Marca marca = marcaRepository.findByDescricaoIgnoreCase(marcaRequestDto.getDescricao());

        if(Objects.nonNull(marca))
            throw new ConflitoException(MensagensPadrao.MARCA_JA_CADASTRADA);
    }

    @Transactional
    public MarcaResponseDto atualizarMarca(Long idMarca, MarcaRequestDto marcaRequestDto){
        validarAtualizacaoMarca(idMarca,marcaRequestDto);
        marcaRequestDto.setId(idMarca);
        Marca marca = modelMapper.map(marcaRequestDto,Marca.class);
        Marca marcaCadastrada = marcaRepository.save(marca);
        return modelMapper.map(marcaCadastrada,MarcaResponseDto.class);
    }

    private void validarAtualizacaoMarca(Long idMarca, MarcaRequestDto marcaRequestDto){
        Marca marca = marcaRepository
                .findByDescricaoAndIdDifferent(marcaRequestDto.getDescricao(), idMarca);

        if(Objects.nonNull(marca))
            throw new ConflitoException(MensagensPadrao.MARCA_JA_CADASTRADA);
    }

    @Transactional
    public void desativarMarca(Long idMarca){
        Marca marca = marcaRepository.findById(idMarca)
                .orElseThrow(new NotFound(MensagensPadrao.MARCA_NOT_FOUND));

        marca.desativar();
        marcaRepository.save(marca);
    }

    @Transactional
    public MarcaResponseDto ativarMarca(Long idMarca){
        Marca marca = marcaRepository.findById(idMarca)
                .orElseThrow(new NotFound(MensagensPadrao.MARCA_NOT_FOUND));

        marca.ativar();
        marca = marcaRepository.save(marca);
        return modelMapper.map(marca, MarcaResponseDto.class);
    }

    private List<MarcaResponseDto> converterMarcasParaResponseDto(List<Marca> marcas){
        return marcas.stream()
                .map(marca -> modelMapper.map(marca,MarcaResponseDto.class))
                .collect(Collectors.toList());
    }
}