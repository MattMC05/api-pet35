package br.com.serratec.exercicio1.model;

import jakarta.persistence.Entity;

@Entity
public class Medico extends Pessoa{

    private Long crm;
    private String especialidade;

    public Long getCrm() {
        return crm;
    }
    public void setCrm(Long crm) {
        this.crm = crm;
    }
    public String getEspecialidade() {
        return especialidade;
    }
    public void setEspecialidade(String especialidade) {
        this.especialidade = especialidade;
    }
    
}
