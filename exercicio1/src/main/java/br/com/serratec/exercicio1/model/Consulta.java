package br.com.serratec.exercicio1.model;

import java.time.LocalDate;
import java.time.LocalTime;

import com.fasterxml.jackson.annotation.JsonBackReference;

import br.com.serratec.exercicio1.enums.StatusConsultaEnum;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;

@Entity
public class Consulta {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private LocalDate dataConsulta;
    private LocalTime horaConsulta;

    @Enumerated
    private StatusConsultaEnum status;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "id_paciente")
    private Paciente paciente;

    @ManyToOne
    @JoinColumn(name = "id_medico")
    private Medico medico;



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDataConsulta() {
        return dataConsulta;
    }

    @PrePersist
    public void prePersistDataConsulta() {
        dataConsulta = LocalDate.now();
        horaConsulta = LocalTime.now();
    }

    public LocalTime getHoraConsulta() {
        return horaConsulta;
    }

    public StatusConsultaEnum getStatus() {
        return status;
    }

    public void setStatus(StatusConsultaEnum status) {
        this.status = status;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    public Medico getMedico() {
        return medico;
    }

    public void setMedico(Medico medico) {
        this.medico = medico;
    }

}
