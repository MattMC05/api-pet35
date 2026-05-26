package br.com.serratec.projeto.dto;

import java.math.BigDecimal;

public record ServicoResponseDTO(

    Long id,
    String descricao,
    BigDecimal valor,
    Integer tempoEstimado 
)

{

}