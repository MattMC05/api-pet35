package br.com.serratec.projeto.model;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;

@Entity
public class Servico {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank(message = "Descricao é Obrigatoria")
    private String descricao;

    private Double valor;

    @NotBlank
    @FutureOrPresent(message = "Data não pode ser passada")
    private LocalDate tempoEstimado;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public LocalDate getTempoEstimado() {
        return tempoEstimado;
    }

    public void setTempoEstimado(LocalDate tempoEstimado) {
        this.tempoEstimado = tempoEstimado;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

}