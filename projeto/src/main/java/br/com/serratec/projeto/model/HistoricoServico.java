package br.com.serratec.projeto.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class HistoricoServico {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "servico_id")
    private Servico servico;

    private String operacao; // INSERT, UPDATE, DELETE

    private String descricao;
    private BigDecimal valor;
    private LocalDateTime tempoEstimado;

    private LocalDateTime dataOperacao;

    public HistoricoServico() {
    }

    public HistoricoServico(Servico servico, String operacao, LocalDateTime dataOperacao) {
        this.servico = servico;
        this.operacao = operacao;
        this.descricao = servico.getDescricao();
        this.valor = servico.getValor();
        this.tempoEstimado = servico.getTempoEstimado();
        this.dataOperacao = dataOperacao;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Servico getServico() {
        return servico;
    }

    public void setServico(Servico servico) {
        this.servico = servico;
    }

    public String getOperacao() {
        return operacao;
    }

    public void setOperacao(String operacao) {
        this.operacao = operacao;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public LocalDateTime getTempoEstimado() {
        return tempoEstimado;
    }

    public void setTempoEstimado(LocalDateTime tempoEstimado) {
        this.tempoEstimado = tempoEstimado;
    }

    public LocalDateTime getDataOperacao() {
        return dataOperacao;
    }

    public void setDataOperacao(LocalDateTime dataOperacao) {
        this.dataOperacao = dataOperacao;
    }
}
