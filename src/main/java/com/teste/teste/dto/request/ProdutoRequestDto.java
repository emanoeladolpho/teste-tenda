package com.teste.teste.dto.request;

import javax.validation.constraints.*;
import java.math.BigDecimal;

public class ProdutoRequestDto {

    private Long id;

    @NotNull(message = "É obrigatório informar a categoria!")
    private Long idCategoria;

    @NotNull(message = "É obrigatório informar a marca!")
    private Long idMarca;

    @NotBlank(message = "É obrigatório informar a descrição!")
    private String descricao;
    private String detalhe;

    @NotBlank(message = "É obrigatório informar o SKU!")
    private String sku;

    @DecimalMin(value = "1", message = "O preço não pode ser menor que 1!")
    @NotNull(message = "É obrigatório informar o preço!")
    private BigDecimal preco;

    @PositiveOrZero(message = "O peso não pode ser menor que zero!")
    private Double pesoGrama;
    @PositiveOrZero(message = "A altura não pode ser menor que zero!")
    private Double alturaCm;
    @PositiveOrZero(message = "A largura não pode ser menor que zero!")
    private Double larguraCm;
    @PositiveOrZero(message = "A profundidade não pode ser menor que zero!")
    private Double profundidadeCm;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(Long idCategoria) {
        this.idCategoria = idCategoria;
    }

    public Long getIdMarca() {
        return idMarca;
    }

    public void setIdMarca(Long idMarca) {
        this.idMarca = idMarca;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getDetalhe() {
        return detalhe;
    }

    public void setDetalhe(String detalhe) {
        this.detalhe = detalhe;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public BigDecimal getPreco() {
        return preco;
    }

    public void setPreco(BigDecimal preco) {
        this.preco = preco;
    }

    public Double getPesoGrama() {
        return pesoGrama;
    }

    public void setPesoGrama(Double pesoGrama) {
        this.pesoGrama = pesoGrama;
    }

    public Double getAlturaCm() {
        return alturaCm;
    }

    public void setAlturaCm(Double alturaCm) {
        this.alturaCm = alturaCm;
    }

    public Double getLarguraCm() {
        return larguraCm;
    }

    public void setLarguraCm(Double larguraCm) {
        this.larguraCm = larguraCm;
    }

    public Double getProfundidadeCm() {
        return profundidadeCm;
    }

    public void setProfundidadeCm(Double profundidadeCm) {
        this.profundidadeCm = profundidadeCm;
    }

}