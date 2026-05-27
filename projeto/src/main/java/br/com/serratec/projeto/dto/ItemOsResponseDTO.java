package br.com.serratec.projeto.dto;

import java.math.BigDecimal;
import br.com.serratec.projeto.model.ItemOs; // Import da nossa entidade N x N

public record ItemOsResponseDTO(

    Long servicoId,
    String descricaoServico,
    BigDecimal valorServico,
    BigDecimal desconto,
    BigDecimal quantidade,
    BigDecimal subtotal
) 

{
    
    public ItemOsResponseDTO(ItemOs item) {
        this(
            item.getServico().getId(),
            item.getServico().getDescricao(),
            item.getServico().getValor(),
            item.getDesconto(),
            item.getQuantidade(),
            item.getSubtotal()
        );
    }
}