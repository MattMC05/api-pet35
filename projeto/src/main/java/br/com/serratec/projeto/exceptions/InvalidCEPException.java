package br.com.serratec.projeto.exceptions;

public class InvalidCEPException extends RuntimeException {
    public InvalidCEPException(String mensagem) {
        super(mensagem);
    }
}