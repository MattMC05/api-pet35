package br.com.serratec.projeto.exceptions;

public class BancoDeDadosException extends RuntimeException {
    public BancoDeDadosException(String mensagem) {
        super(mensagem);
    }
}