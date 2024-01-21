package com.teste.teste.model;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class DetalhesErro {

    private String titulo;
    private Long status;
    private Long timestamp;
    private String menssagemUsuario;
    private String menssagemDesenvolvedor;

    private List<String> mensagenUsuariosLista;

    public List<String> getMensagenUsuariosLista() {
        return mensagenUsuariosLista;
    }

    public void setMensagenUsuariosLista(List<String> mensagenUsuariosLista) {
        this.mensagenUsuariosLista = mensagenUsuariosLista;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Long getStatus() {
        return status;
    }

    public void setStatus(Long status) {
        this.status = status;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public String getMenssagemDesenvolvedor() {
        return menssagemDesenvolvedor;
    }

    public void setMenssagemDesenvolvedor(String menssagemDesenvolvedor) {
        this.menssagemDesenvolvedor = menssagemDesenvolvedor;
    }

    public String getMenssagemUsuario() {
        return menssagemUsuario;
    }

    public void setMenssagemUsuario(String menssagemUsuario) {
        this.menssagemUsuario = menssagemUsuario;
    }
}