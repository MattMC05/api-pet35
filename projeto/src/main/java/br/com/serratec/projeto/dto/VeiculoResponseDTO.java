package br.com.serratec.projeto.dto;

import br.com.serratec.projeto.model.Veiculo;

public record VeiculoResponseDTO(
    
    Long id,
    String placa,
    String marca,
    String modelo,
    Integer ano,
    String cor,
    ClienteResponseDTO cliente
) 

{
    public VeiculoResponseDTO(Veiculo v) {
        this(v.getId(), v.getPlaca(), v.getMarca(), v.getModelo(), v.getAno(), v.getCor(), new ClienteResponseDTO(v.getCliente()));
    }

}