package com.teste.teste.listener;

import com.teste.teste.model.Produto;

import javax.persistence.PrePersist;

public class ProdutoListener {

    @PrePersist
    void antesDeSalvar(Produto produto){
        produto.ativar();
    }

}