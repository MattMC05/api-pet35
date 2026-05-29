package br.com.serratec.projeto.dto;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ServicoRequestDTO(

    @NotBlank(message = "Descrição é obrigatória")
    String descricao,

    @NotNull(message = "Valor é obrigatório")
    @PositiveOrZero(message = "Valor deve ser positivo")
    BigDecimal valor,

    @FutureOrPresent(message = "Data não pode ser passada")
    LocalDateTime tempoEstimado

) 

{

}