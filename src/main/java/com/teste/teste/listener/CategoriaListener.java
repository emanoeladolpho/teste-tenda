package com.teste.teste.listener;

import com.teste.teste.model.Categoria;

import javax.persistence.PrePersist;

public class CategoriaListener {

    @PrePersist
    void antesDeSalvar(Categoria categoria){
        categoria.ativar();
    }
}
