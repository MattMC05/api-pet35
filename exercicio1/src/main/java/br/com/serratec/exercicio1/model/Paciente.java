package br.com.serratec.exercicio1.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;

@Entity
public class Paciente extends Pessoa {

    @OneToMany(mappedBy = "paciente")
    @JsonManagedReference
    private List<Consulta> consultas;

    public List<Consulta> getConsultas() {
        return consultas;
    }
    
}
