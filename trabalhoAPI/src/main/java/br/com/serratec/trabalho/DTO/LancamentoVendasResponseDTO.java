package br.com.serratec.trabalho.DTO;

import java.time.LocalDate;


public record LancamentoVendasResponseDTO(LocalDate data,Double valor, String nomeVendedor) {

}
