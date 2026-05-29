package br.com.serratec.projeto.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record ClienteRequestDTO(

    @NotBlank(message = "Nome é obrigatório")
    String nome,

    @NotBlank(message = "Telefone é obrigatório")
    @Pattern(regexp = "\\d{10,11}", message = "Telefone deve conter 10 ou 11 dígitos")
    String telefone,

    @NotBlank(message = "Email é obrigatório")
    @Email(message = "Email inválido")
    String email,

    @NotBlank(message = "CPF é obrigatório")
    @Pattern(regexp = "\\d{11}", message = "CPF deve conter 11 dígitos")
    String cpf,

    @NotBlank(message = "CEP é obrigatório")
    @Pattern(regexp = "\\d{8}", message = "CEP deve conter 8 dígitos")
    String cep
) 

{
    // Criamos este construtor compacto para remover qualquer caractere que não seja número e garantir que o código não quebre se o CPF e o CEP for nulo.
    public ClienteRequestDTO {
        cpf = (cpf != null) ? cpf.replaceAll("\\D", "") : null;
        cep = (cep != null) ? cep.replaceAll("\\D", "") : null;
    }

}