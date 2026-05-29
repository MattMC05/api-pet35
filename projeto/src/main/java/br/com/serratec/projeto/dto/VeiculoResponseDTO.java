package br.com.serratec.projeto.dto;

import br.com.serratec.projeto.model.Veiculo;

public record VeiculoResponseDTO(
    
    Long id,
    String placa,
    String marca,
    String modelo,
    Integer ano,
    String cor,
    String nomeProprietario,
    String telefone,
    String email,
    String cpf
) 

{
    public VeiculoResponseDTO(Veiculo v) {
        this(v.getId(), v.getPlaca(), v.getMarca(), v.getModelo(), v.getAno(), v.getCor(), v.getCliente().getNome(),v.getCliente().getTelefone(),v.getCliente().getEmail(),v.getCliente().getCpf());
    }

}