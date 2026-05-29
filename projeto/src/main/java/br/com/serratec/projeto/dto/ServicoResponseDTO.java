package br.com.serratec.projeto.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import br.com.serratec.projeto.model.Servico;

public record ServicoResponseDTO(

    Long id,
    String descricao,
    BigDecimal valor,
    LocalDateTime tempoEstimado 
    
)

{

    public ServicoResponseDTO(Servico s) {
        this(s.getId(), s.getDescricao(), s.getValor(), s.getTempoEstimado());
    }
    
}