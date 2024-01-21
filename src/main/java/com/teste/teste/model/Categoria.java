package com.teste.teste.model;

import com.teste.teste.listener.CategoriaListener;

import javax.persistence.*;
import java.util.Objects;

@EntityListeners(CategoriaListener.class)
@Entity
public class Categoria {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "COD_CATEGORIA")
    private Long id;

    @Column(name = "DESCRICAO")
    private String descricao;

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

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
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
        Categoria categoria = (Categoria) o;
        return Objects.equals(id, categoria.id) && Objects.equals(descricao, categoria.descricao);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, descricao);
    }
}