package br.com.serratec.projeto.dto;

import br.com.serratec.projeto.model.Cliente;

public record ClienteResponseDTO(

    Long id,
    String nome,
    String telefone,
    String email,
    String cpf, 
    String logradouro, 
    String bairro, 
    String localidade, 
    String uf,
    EnderecoDTO endereco
) 

{
    public ClienteResponseDTO(Cliente c) {
        this(c.getId(), c.getNome(), c.getEmail(), c.getTelefone(), 
             c.getLogradouro(), c.getBairro(), c.getLocalidade(), c.getUf());
    }

}