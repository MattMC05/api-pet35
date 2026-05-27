package br.com.serratec.projeto.dto;

import java.math.BigDecimal;
import java.util.List;
import br.com.serratec.projeto.enums.OsStatus;
import br.com.serratec.projeto.model.ItemOs;
import br.com.serratec.projeto.model.OrdemDeServico;

public record OrdemServicoResponseDTO(

    Long numeroOs,
    OsStatus status,
    String nomeCliente,
    String placaVeiculo,
    List<ItemOs> itensRealizados,
    BigDecimal valorTotal

)

{

    public OrdemServicoResponseDTO(OrdemDeServico os) {
        this(os.getId(), os.getStatus(), os.getCliente().getNome(), os.getVeiculo().getPlaca(), os.getItemsOs(), os.getValorTotal());
    }

}