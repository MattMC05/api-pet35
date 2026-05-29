package br.com.serratec.projeto.dto;

import br.com.serratec.projeto.enums.OsStatus;
import br.com.serratec.projeto.model.OrdemDeServico;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;

// DTO Principal: A abertura da OS (Ordem de Serviço)
public record OrdemServicoRequestDTO(

    @Enumerated(EnumType.STRING)
    OsStatus status,

    @NotNull(message = "ID do cliente é obrigatório")
    Long clienteId,

    @NotNull(message = "ID do veículo é obrigatório")
    Long veiculoId

    //@NotNull(message = "Itens da OS são obrigatórios")
    //List<ItemOsRequestDTO> itensRealizados
)

{

    public OrdemServicoRequestDTO(OrdemDeServico os) {
        this(os.getStatus(),os.getCliente().getId(), os.getVeiculo().getId());
    }

    
}