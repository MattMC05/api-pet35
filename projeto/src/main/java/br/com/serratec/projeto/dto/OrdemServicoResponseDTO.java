package br.com.serratec.projeto.dto;

import java.math.BigDecimal;
import java.util.List;
import br.com.serratec.projeto.enums.OsStatus;

// Quando o utilizador faz um GET /ordens-servico/1, ele vai ver um resumo.
public record OrdemServicoResponseDTO(

    Long numeroOs,
    OsStatus status,
    String nomeCliente,
    String placaVeiculo,
    List<ItemOsResponseDTO> itensRealizados, // Lista detalhada com descrição e subtotal
    BigDecimal valorTotal // A soma de tudo
)

{

}