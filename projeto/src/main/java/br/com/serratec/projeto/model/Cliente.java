package br.com.serratec.projeto.model;

import java.util.Map;

import org.hibernate.validator.constraints.br.CPF;

import br.com.serratec.projeto.dto.ClienteRequestDTO;
import br.com.serratec.projeto.dto.ViaCepDTO;
import br.com.serratec.projeto.service.ViaCepService;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

@Entity
@Table(name = "clientes")
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank(message = "Nome é obrigatório")
    private String nome;

    @NotBlank(message = "Telefone é obrigatório")
    @Pattern(regexp = "\\d{10,11}", message = "Telefone deve conter 10 ou 11 dígitos")
    private String telefone;

    @NotBlank(message = "Email é obrigatório")
    @Email(message = "Email inválido")
    @Column(unique = true)
    private String email;

    @NotBlank(message = "CPF é obrigatório")
    @Pattern(regexp = "\\d{11}", message = "CPF deve conter 11 dígitos")
    @CPF
    @Column(unique = true, length = 11)
    private String cpf;

    public String getEndereco;

    public Endereco endereco;

    public Cliente() {
    }

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

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public Endereco getEndereco() {
        return getEndereco();
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    private void preencherEndereco(Cliente cliente, String cep, ViaCepService viaCepService) {
        ViaCepDTO enderecoViaCep = viaCepService.consultarCep(cep);
        if (enderecoViaCep != null && enderecoViaCep.cep() != null) {
            // 💡 Veja como fica agora:
            cliente.getEndereco().setCep(cep);
            cliente.getEndereco().setLogradouro(enderecoViaCep.logradouro());
            cliente.getEndereco().setBairro(enderecoViaCep.bairro());
            cliente.getEndereco().setCidade(enderecoViaCep.localidade());
            cliente.getEndereco().setUf(enderecoViaCep.uf());
        } else {
            throw new IllegalArgumentException("CEP inválido ou não encontrado: " + cep);
        }
    }

    private void copiarDadosBase(ClienteRequestDTO dto, Cliente cliente) {
        cliente.setNome(dto.nome());
        cliente.setEmail(dto.email());
        cliente.setTelefone(dto.telefone());
        cliente.setCpf(dto.cpf());
    }

    public Object getCep() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getCep'");
    }

    public void setC(String cep) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setC'");
    }

    public Map<String, ?> id() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'id'");
    }

    public void setLogradouro(String logradouro) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setLogradouro'");
    }

    public void setBairro(String bairro) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setBairro'");
    }

    public void setCidade(String localidade) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setCidade'");
    }

    public void setUf(String uf) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setUf'");
    }
}

