package com.teste.teste.dto.request;

import javax.validation.constraints.NotBlank;

public class MarcaRequestDto {

    private Long id;

    @NotBlank(message = "É obrigatório informar a descrição!")
    private String descricao;

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
}
