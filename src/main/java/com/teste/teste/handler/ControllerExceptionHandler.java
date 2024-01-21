package com.teste.teste.handler;

import com.teste.teste.exceptions.ConflitoException;
import com.teste.teste.exceptions.NotFound;
import com.teste.teste.model.DetalhesErro;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class ControllerExceptionHandler {

    // -------------------- EXCEPTIONS JÁ EMBUTIDAS NO SPRING -------------------------------

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<DetalhesErro> handleConstraintsException(ConstraintViolationException ex){

        List<String> errors = new ArrayList<>();

        for (ConstraintViolation<?> violation : ex.getConstraintViolations()) {
            String error = violation.getMessage();
            errors.add(error);
        }

        String errorMessage = String.join(", ", errors);

        DetalhesErro erro = new DetalhesErro();
        erro.setTimestamp(System.currentTimeMillis());
        erro.setTitulo("Erro de restrição");
        erro.setStatus(400L);
        erro.setTimestamp(System.currentTimeMillis());
        erro.setMenssagemUsuario(errorMessage);
        erro.setMenssagemDesenvolvedor(gerarMenssagemDeErro(ex));

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<DetalhesErro> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex){

        BindingResult bindingResult = ex.getBindingResult();
        List<String> errors = new ArrayList<>();

        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            errors.add(fieldError.getDefaultMessage());
        }

        String errorMessage = String.join(", ", errors);

        DetalhesErro erro = new DetalhesErro();
        erro.setTimestamp(System.currentTimeMillis());
        erro.setTitulo("Erro de validações");
        erro.setStatus(400L);
        erro.setMenssagemUsuario(errorMessage);
        erro.setMenssagemDesenvolvedor(gerarMenssagemDeErro(ex));

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
    }

    // ------------------------------- EXCEPTIONS CUSTOMIZADAS----------------------------------------

    @ExceptionHandler({Exception.class})
    public ResponseEntity<DetalhesErro> handleNullPointerException(NullPointerException e) {
        DetalhesErro erro = new DetalhesErro();
        erro.setTitulo("NullPointerException!");
        erro.setMenssagemUsuario("Ocorreu um erro no sistema! Tente novamente mais tarde!");
        erro.setStatus(500L);
        erro.setTimestamp(System.currentTimeMillis());
        erro.setMenssagemDesenvolvedor(gerarMenssagemDeErro(e));
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(erro);
    }


    @ExceptionHandler(NotFound.class)
    public ResponseEntity<DetalhesErro> handleNotFoundException(NotFound exception){
        DetalhesErro erro = new DetalhesErro();
        erro.setTitulo("O objeto não encontrado!");
        erro.setStatus(404L);
        erro.setMenssagemDesenvolvedor(exception.getMessage());
        erro.setMenssagemUsuario(exception.getMessage());
        erro.setTimestamp(System.currentTimeMillis());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(erro);
    }

    @ExceptionHandler(ConflitoException.class)
    public ResponseEntity<DetalhesErro> handleConflitoException(ConflitoException ex){

        DetalhesErro erro = new DetalhesErro();
        erro.setTitulo("Ocorreu um conflito!");
        erro.setMenssagemDesenvolvedor(ex.getMessage());
        erro.setStatus(409L);
        erro.setTimestamp(System.currentTimeMillis());

        return ResponseEntity.status(HttpStatus.CONFLICT).body(erro);
    }

    @ExceptionHandler(NumberFormatException.class)
    public ResponseEntity<DetalhesErro> handlerNumberFormatException(NumberFormatException ex){
        String titulo;
        String msgUser;

        titulo = "Parametro invalido";
        msgUser = "Esperava um numero, mas a entrada foi uma String";

        ex.printStackTrace();

        DetalhesErro erro = new DetalhesErro();
        erro.setMenssagemUsuario(msgUser);
        erro.setMenssagemDesenvolvedor(gerarMenssagemDeErro(ex));
        erro.setStatus(400L);
        erro.setTitulo(titulo);
        erro.setTimestamp(System.currentTimeMillis());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<DetalhesErro> handleMissingServletRequestParameterException(MissingServletRequestParameterException ex){
        String parametrosAusentes = ex.getParameterName();

        DetalhesErro erro = new DetalhesErro();
        erro.setTitulo("Erro de preenchimento");
        erro.setStatus(400L);
        erro.setTimestamp(System.currentTimeMillis());
        erro.setMenssagemUsuario("Ocorreu um erro no sistema! Algumas inforamções espradas não foram fornecidas!");
        erro.setMenssagemDesenvolvedor("Os seguintes parâmetros estão ausentes: " + parametrosAusentes);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<DetalhesErro> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex){

        String argumentoComErro = ex.getName();
        String mensagemDeErro = "O argumento '" + argumentoComErro + "' possui um tipo inválido.";

        DetalhesErro erro = new DetalhesErro();
        erro.setTitulo("Parâmetro inválido");
        erro.setMenssagemUsuario("Ocorreu um erro inesperado no sistema!Tente novamente mais tarde!");
        erro.setMenssagemDesenvolvedor(mensagemDeErro);
        erro.setTimestamp(System.currentTimeMillis());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
    }

    public String gerarMenssagemDeErro(Exception ex){

        // Obter a pilha de chamadas (stack trace) da exceção
        StackTraceElement[] stackTrace = ex.getStackTrace();

        if (stackTrace.length > 0) {

            // Obter a primeira linha da pilha de chamadas
            StackTraceElement firstStackTraceElement = stackTrace[0];

            String className = firstStackTraceElement.getClassName();
            String methodName = firstStackTraceElement.getMethodName();
            int lineNumber = firstStackTraceElement.getLineNumber();

            return "Erro na classe " + className + ", método " + methodName + ", linha " + lineNumber;
        }

        return "Ocorreu um erro desconhecido. Tente novamente mais tarde!";

    }
}