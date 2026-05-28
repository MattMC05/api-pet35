package br.com.serratec.projeto.dto;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;

public record ServicoRequestDTO(

    @NotBlank(message = "Descrição é obrigatória")
    String descricao,

    @NotNull(message = "Valor é obrigatório")
    @PositiveOrZero(message = "Valor deve ser positivo")
    BigDecimal valor,

    @NotBlank(message = "Tempo estimado é obrigatório")
    @FutureOrPresent(message = "Data não pode ser passada")
    @Positive(message = "Tempo deve ser positivo")
    Integer tempoEstimado, // em minutos

    @NotNull @Positive BigDecimal valorBase // Previne que o funcionário cadastre valor negativo
) 

{

}