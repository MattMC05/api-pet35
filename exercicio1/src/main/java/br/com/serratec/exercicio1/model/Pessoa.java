package br.com.serratec.exercicio1.model;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@MappedSuperclass
public abstract class Pessoa {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected Long id;

    @NotBlank(message = "Preencha o nome")
    @Size(max = 60, message = "Limite de 60 caracteres")
    protected String nome;

    @Email(message = "Insira um email, ex.: nome@email.com")
    protected String email;

    @NotBlank(message = "Preencha o telefone")
    @Size(max = 11, message = "Tamanho máximo de 11 dígitos")
    @Pattern(regexp = "^\\d+$", message = "O campo não pode conter letras")
    protected String telefone;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

}
