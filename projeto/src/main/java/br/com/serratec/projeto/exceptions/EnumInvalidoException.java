package br.com.serratec.projeto.exceptions;

public class EnumInvalidoException extends RuntimeException {
    public EnumInvalidoException(String mensagem) {
        super(mensagem);
    }
}