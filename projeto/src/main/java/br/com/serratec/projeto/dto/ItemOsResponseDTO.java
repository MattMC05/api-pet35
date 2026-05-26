package br.com.serratec.projeto.dto;

import java.math.BigDecimal;
import br.com.serratec.projeto.model.ItemOrdemServico; // Import da nossa entidade N x N

public record ItemOsResponseDTO(

    Long servicoId,
    String descricaoServico,
    BigDecimal valorServico,
    BigDecimal desconto,
    Integer quantidade,
    BigDecimal subtotal
) 

{
    // Construtor feito para facilitar o mapeamento na camada de Service, direto e blindado.
    public ItemOsResponseDTO(ItemOrdemServico item) {
        this(
            item.getServico().getId(),
            item.getServico().getDescricao(),
            item.getValorServico(),
            item.getDesconto(),
            item.getQuantidade(),
            item.getSubtotal()
        );
    }
}