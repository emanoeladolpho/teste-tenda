package com.teste.teste.service;

import com.teste.teste.dto.request.ProdutoRequestDto;
import com.teste.teste.dto.response.ProdutoResponseDto;
import com.teste.teste.exceptions.ConflitoException;
import com.teste.teste.exceptions.NotFound;
import com.teste.teste.model.Categoria;
import com.teste.teste.model.Marca;
import com.teste.teste.model.Produto;
import com.teste.teste.repository.CategoriaRepository;
import com.teste.teste.repository.MarcaRepository;
import com.teste.teste.repository.ProdutoRepository;
import com.teste.teste.util.MensagensPadrao;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class ProdutoService {

    private final ProdutoRepository produtoRepository;
    private final CategoriaRepository categoriaRepository;
    private final MarcaRepository marcaRepository;

    private final ModelMapper modelMapper = new ModelMapper();

    public ProdutoService(ProdutoRepository produtoRepository,
                          CategoriaRepository categoriaRepository,
                          MarcaRepository marcaRepository) {
        this.produtoRepository = produtoRepository;
        this.categoriaRepository = categoriaRepository;
        this.marcaRepository = marcaRepository;
    }

    public Page<ProdutoResponseDto> listarAtivos(Pageable pageable){
        Page<Produto> produtos = produtoRepository.findAllByAtivoIsTrue(pageable);
        return converterProdutosParaResponseDto(produtos,pageable);
    }

    public Page<ProdutoResponseDto> listarInativos(Pageable pageable){
        Page<Produto> produtos = produtoRepository.findAllByAtivoIsFalse(pageable);
        return converterProdutosParaResponseDto(produtos,pageable);
    }

    public ProdutoResponseDto buscarById(Long id){
        Produto produto = produtoRepository.findById(id)
                .orElseThrow(new NotFound(MensagensPadrao.PRODUTO_NOT_FOUND));
        return modelMapper.map(produto,ProdutoResponseDto.class);
    }

    public ProdutoResponseDto buscarPorSku(String sku){
        Produto produto = produtoRepository.findBySku(sku);
        return modelMapper.map(produto,ProdutoResponseDto.class);
    }

    public Page<ProdutoResponseDto> buscarPorCategoria(Long idCategoria, Pageable pageable){
        Page<Produto> produtos =  produtoRepository.findAllByCategoriaIdAndAtivoIsTrue(idCategoria,pageable);
        return converterProdutosParaResponseDto(produtos,pageable);
    }

    public Page<ProdutoResponseDto> buscarPorMarca(Long idMarca, Pageable pageable){
        Page<Produto> produtos = produtoRepository.findAllByMarcaIdAndAtivoIsTrue(idMarca,pageable);
        return converterProdutosParaResponseDto(produtos, pageable);
    }

    @Transactional
    public ProdutoResponseDto cadastrar(ProdutoRequestDto produtoRequestDto){
        validarCadastroProduto(produtoRequestDto);
        Produto produto = modelMapper.map(produtoRequestDto,Produto.class);
        Produto produtoCadastrado = produtoRepository.save(produto);
        return modelMapper.map(produtoCadastrado,ProdutoResponseDto.class);
    }

    private void validarCadastroProduto(ProdutoRequestDto produtoRequestDto){
        Produto produto = produtoRepository.findBySku(produtoRequestDto.getSku());

        if(Objects.nonNull(produto))
            throw new ConflitoException(MensagensPadrao.PRODUTO_JA_CADASTRADO);

        Categoria categoria = categoriaRepository.findById(produtoRequestDto.getIdCategoria())
                .orElseThrow(new NotFound(MensagensPadrao.CATEGORIA_NOT_FOUND));

        if(Boolean.FALSE.equals(categoria.getAtivo()))
            throw new ConflitoException("Não é possível cadastrar o produto nesta categoria!");

        Marca marca = marcaRepository.findById(produtoRequestDto.getIdMarca())
                .orElseThrow(new NotFound(MensagensPadrao.MARCA_NOT_FOUND));

        if(Boolean.FALSE.equals(marca.getAtivo()))
            throw new ConflitoException("Não é possível cadastrar o produto nesta marca!");
    }

    @Transactional
    public ProdutoResponseDto atualizarProduto(Long idProduto, ProdutoRequestDto produtoRequestDto){
        validarAtualizacaoProduto(idProduto,produtoRequestDto);
        produtoRequestDto.setId(idProduto);
        Produto produto = modelMapper.map(produtoRequestDto,Produto.class);
        Produto produtoCadastrado = produtoRepository.save(produto);
        return modelMapper.map(produtoCadastrado,ProdutoResponseDto.class);
    }

    private void validarAtualizacaoProduto(Long idProduto, ProdutoRequestDto produtoRequestDto){
        Produto produto = produtoRepository.findBySkuAndDifferentId(produtoRequestDto.getSku(),idProduto);

        if(Objects.nonNull(produto))
            throw new ConflitoException(MensagensPadrao.PRODUTO_JA_CADASTRADO);
    }

    @Transactional
    public void desativarProduto(Long idProduto){
        Produto produto = produtoRepository
                .findById(idProduto).orElseThrow(new NotFound(MensagensPadrao.PRODUTO_NOT_FOUND));

        produto.desativar();
        produtoRepository.save(produto);
    }

    @Transactional
    public ProdutoResponseDto ativarProduto(Long idProduto){
        Produto produto = produtoRepository
                .findById(idProduto).orElseThrow(new NotFound(MensagensPadrao.PRODUTO_NOT_FOUND));

        produto.ativar();
        produto = produtoRepository.save(produto);
        return modelMapper.map(produto,ProdutoResponseDto.class);
    }

    private Page<ProdutoResponseDto> converterProdutosParaResponseDto(Page<Produto> produtos, Pageable pageable ){

        List<ProdutoResponseDto> produtosConvertidos = produtos.getContent().stream()
                .map(produto -> modelMapper.map(produto,ProdutoResponseDto.class))
                .collect(Collectors.toList());

        return new PageImpl<>(produtosConvertidos,pageable, produtos.getTotalElements());
    }
}