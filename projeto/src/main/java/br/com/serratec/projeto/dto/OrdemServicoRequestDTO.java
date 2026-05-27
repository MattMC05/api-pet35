package br.com.serratec.projeto.dto;

import br.com.serratec.projeto.enums.OsStatus;
import jakarta.validation.constraints.NotNull;

// DTO Principal: A abertura da OS (Ordem de Serviço)
public record OrdemServicoRequestDTO(

    @NotNull(message = "ID do cliente é obrigatório")
    Long clienteId,

    @NotNull(message = "ID do veículo é obrigatório")
    Long veiculoId,

    @NotNull(message = "Status é obrigatório")
    OsStatus status//,

    //@NotNull(message = "Itens da OS são obrigatórios")
    //List<ItemOsRequestDTO> itensRealizados
)

{

}