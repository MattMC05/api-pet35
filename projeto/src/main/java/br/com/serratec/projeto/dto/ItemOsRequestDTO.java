package br.com.serratec.projeto.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;

// DTO Auxiliar: O mecânico diz qual serviço fez, quantos e se deu desconto.
public record ItemOsRequestDTO(

    @NotNull(message = "ID do serviço é obrigatório")
    Long servicoId,

    @NotNull(message = "Quantidade é obrigatória")
    @Positive(message = "Quantidade deve ser positiva")
    Integer quantidade,

    @NotNull(message = "Desconto é obrigatório")
    @DecimalMin(value = "0.00", message = "Desconto deve ser positivo")
    BigDecimal desconto
) 

{
    public ItemOsRequestDTO {
        // Regra de segurança: Se não mandar desconto, assume 0 em vez de nulo
        desconto = (desconto != null) ? desconto : BigDecimal.ZERO;
    }

}