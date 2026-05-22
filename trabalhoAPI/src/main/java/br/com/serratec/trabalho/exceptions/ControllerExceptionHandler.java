package br.com.serratec.trabalho.exceptions;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.jspecify.annotations.Nullable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.http.converter.HttpMessageNotReadableException;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected @Nullable ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
            HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        List<String> erros = new ArrayList<>();

        for (FieldError erro : ex.getBindingResult().getFieldErrors()) {
            erros.add(erro.getField() + ":" + erro.getDefaultMessage());
        }

        ErroResposta er = new ErroResposta(status.value(), "Existem erros na requisição!", LocalDateTime.now(), erros);
        return super.handleExceptionInternal(ex, er, headers, status, request);
    }
    
    @Override
    protected @Nullable ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
            HttpHeaders headers, HttpStatusCode status, WebRequest request) {
                ErroResposta er = new ErroResposta(status.value(), "Erro na Requisição: "+ex.getMessage(), LocalDateTime.now());
        return super.handleExceptionInternal(ex, er, headers, status, request);
    }

    @ExceptionHandler(LancamentoException.class)
    protected @Nullable ResponseEntity<Object> handleLancamentoException(LancamentoException ex){
        ErroResposta er = new ErroResposta(HttpStatus.NOT_FOUND.value(), "Erro na Requisição: "+ex.getMessage(), LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(er);
    }

}