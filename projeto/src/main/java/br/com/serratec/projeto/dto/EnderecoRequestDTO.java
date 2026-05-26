package br.com.serratec.projeto.dto;

import br.com.serratec.projeto.model.Endereco;

public record EnderecoRequestDTO(
    String cep,
    String logradouro,
    String bairro,
    String localidade,
    String uf
) 

{
    public EnderecoRequestDTO(Endereco endereco){
        this(
            endereco.getCep(), 
            endereco.getLogradouro(), 
            endereco.getBairro(), 
            endereco.getLocalidade(), 
            endereco.getUf());
    }

}