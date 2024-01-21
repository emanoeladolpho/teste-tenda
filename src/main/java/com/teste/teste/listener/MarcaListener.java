package com.teste.teste.listener;

import com.teste.teste.model.Marca;

import javax.persistence.PrePersist;

public class MarcaListener {

    @PrePersist
    void anteDeSalvar(Marca marca){
        marca.ativar();
    }
}
