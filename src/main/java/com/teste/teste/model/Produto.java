package com.teste.teste.model;

import com.teste.teste.listener.ProdutoListener;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Objects;

@EntityListeners(ProdutoListener.class)
@Entity
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "COD_PRODUTO")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "FK_COD_CATEGORIA")
    private Categoria categoria;

    @ManyToOne
    @JoinColumn(name = "FK_COD_MARCA")
    private Marca marca;

    @Column(name = "DESCRICAO")
    private String descricao;

    @Column(name = "DETALHE ")
    private String detalhe;

    @Column(name = "SKU")
    private String sku;

    @Column(name = "PRECO")
    private BigDecimal preco;

    @Column(name = "PESO_GRAMA")
    private Double pesoGrama;

    @Column(name = "ALTURA_CM")
    private Double alturaCm;

    @Column(name = "LARGURA_CM")
    private Double larguraCm;

    @Column(name = "PROFUNDIDADE_CM")
    private Double profundidadeCm;

    @Column(name = "ATIVO")
    private Boolean ativo;

    public void ativar(){
        this.ativo = true;
    }

    public void desativar(){
        this.ativo = false;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public Marca getMarca() {
        return marca;
    }

    public void setMarca(Marca marca) {
        this.marca = marca;
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

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Produto produto = (Produto) o;
        return Objects.equals(id, produto.id) && Objects.equals(categoria, produto.categoria) && Objects.equals(marca, produto.marca) && Objects.equals(descricao, produto.descricao) && Objects.equals(detalhe, produto.detalhe) && Objects.equals(sku, produto.sku) && Objects.equals(pesoGrama, produto.pesoGrama) && Objects.equals(alturaCm, produto.alturaCm) && Objects.equals(larguraCm, produto.larguraCm) && Objects.equals(profundidadeCm, produto.profundidadeCm) && Objects.equals(ativo, produto.ativo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, categoria, marca, descricao, detalhe, sku, pesoGrama, alturaCm, larguraCm, profundidadeCm, ativo);
    }
}