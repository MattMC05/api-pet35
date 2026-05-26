package br.com.serratec.projeto.dto;

import br.com.serratec.projeto.model.Veiculo;

public record VeiculoResponseDTO(
    
    Long id,
    String placa,
    String marca,
    String modelo,
    Integer ano,
    String cor, 
    String nomeProprietario, // Ao listar os veículos deverá exibir o nome do dono ou proprietário
    ClienteResponseDTO cliente
) 

{
    public VeiculoResponseDTO(Veiculo v) {
        this(v.getId(), v.getPlaca(), v.getMarca(), v.getModelo(), v.getCliente().getNome());
    }

}