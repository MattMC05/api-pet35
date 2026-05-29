package br.com.serratec.projeto.dto;

import br.com.serratec.projeto.model.Cliente;

public record ClienteResponseDTO(

    Long id,
    String nome,
    String telefone,
    String email,
    String cpf/*, 
    String logradouro, 
    String bairro, 
    String localidade, 
    String uf*/
)

{
    public ClienteResponseDTO(Cliente c) {
        this(c.getId(), c.getNome(), c.getTelefone(), c.getEmail(), c.getCpf()/*, c.getEndereco().getLogradouro(), c.getEndereco().getBairro(), c.getEndereco().getLocalidade(), c.getEndereco().getUf()*/);
    }

}