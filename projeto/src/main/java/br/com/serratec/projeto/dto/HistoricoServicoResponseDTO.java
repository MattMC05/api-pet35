package br.com.serratec.projeto.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import br.com.serratec.projeto.model.HistoricoServico;

public record HistoricoServicoResponseDTO(
    Long id,
    Long servicoId,
    String operacao,
    String descricao,
    BigDecimal valor,
    LocalDateTime tempoEstimado,
    LocalDateTime dataOperacao
) {

    public HistoricoServicoResponseDTO(HistoricoServico h) {
        this(
            h.getId(),
            h.getServico() != null ? h.getServico().getId() : null,
            h.getOperacao(),
            h.getDescricao(),
            h.getValor(),
            h.getTempoEstimado(),
            h.getDataOperacao()
        );
    }
}
