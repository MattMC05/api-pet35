package br.com.serratec.projeto.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

@Entity
public class Servico {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank(message = "Descrição é obrigatória")
    private String descricao;

    @NotNull(message = "Valor é obrigatório")
    @PositiveOrZero(message = "Valor deve ser positivo")
    private BigDecimal valor;

    @NotBlank(message = "Tempo estimado é obrigatório")
    @FutureOrPresent(message = "Data não pode ser passada")
    @Positive(message = "Tempo deve ser positivo")
    private LocalDate tempoEstimado;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
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