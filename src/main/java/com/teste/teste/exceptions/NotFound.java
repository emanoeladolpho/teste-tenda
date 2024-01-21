package com.teste.teste.exceptions;

import java.util.function.Supplier;

public class NotFound extends RuntimeException implements Supplier<NotFound> {
    private final String mensagem;

    public NotFound(String message){
        super(message);
        this.mensagem = message;
    }

    @Override
    public NotFound get() {
        return new NotFound(mensagem);
    }
}
