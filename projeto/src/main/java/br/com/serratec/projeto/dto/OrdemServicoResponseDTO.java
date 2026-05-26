package br.com.serratec.projeto.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import br.com.serratec.projeto.model.enums.StatusOS; // O seu Enum

// Quando o utilizador faz um GET /ordens-servico/1, ele vai ver um resumo.
public record OrdemServicoResponseDTO(

    Long numeroOs,
    StatusOS status,
    String nomeCliente,
    String placaVeiculo,
    List<ItemOsResponseDTO> itensRealizados, // Lista detalhada com descrição e subtotal
    BigDecimal valorTotal // A soma de tudo
)

{

}