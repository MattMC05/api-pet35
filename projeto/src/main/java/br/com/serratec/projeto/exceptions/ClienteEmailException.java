package br.com.serratec.projeto.exceptions;

public class ClienteEmailException extends RuntimeException{
    public ClienteEmailException(String mensagem) {
        super(mensagem);
    }
}
