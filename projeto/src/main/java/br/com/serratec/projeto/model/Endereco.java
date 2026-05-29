package br.com.serratec.projeto.model;
import jakarta.persistence.Entity;

import jakarta.persistence.Id;
import jakarta.validation.constraints.Pattern;

@Entity
public class Endereco {
    @Pattern(regexp = "\\d{8}", message = "CEP deve conter 8 dígitos")
    @Id
    private String cep;

    private String logradouro;
    private String bairro;
    private String localidade;
    private String uf;
    
    public String getLogradouro() {
        return logradouro;
    }
    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }
    public String getBairro() {
        return bairro;
    }
    public void setBairro(String bairro) {
        this.bairro = bairro;
    }
    public String getLocalidade() {
        return localidade;
    }
    public void setLocalidade(String localidade) {
        this.localidade = localidade;
    }
    public String getUf() {
        return uf;
    }
    public void setUf(String uf) {
        this.uf = uf;
    }
    public String getCep() {
        return cep;
    }
    public void setCep(String cep) {
        this.cep = cep;
    }
    
}