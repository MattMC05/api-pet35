package br.com.serratec.projeto.exceptions;

public class InvalidDataException extends RuntimeException {
    public InvalidDataException(String mensagem) {
        super(mensagem);
    }
}