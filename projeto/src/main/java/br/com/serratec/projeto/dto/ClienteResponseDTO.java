package br.com.serratec.projeto.dto;

import br.com.serratec.projeto.model.Cliente;

public record ClienteResponseDTO(
    Long id,
    String nome,
    String email,
    String telefone,
    String cpf,
    String cep,
    String logradouro,
    String bairro,
    String cidade,
    String uf
) {
    public ClienteResponseDTO(Cliente cliente) {
        this(
            cliente.getId(), 
            cliente.getNome(), 
            cliente.getEmail(), 
            cliente.getTelefone(), 
            cliente.getCpf(), 
            
            // Verifica se o endereço não é nulo antes de puxar os dados
            cliente.getEndereco() != null ? cliente.getEndereco().getCep() : null,
            cliente.getEndereco() != null ? cliente.getEndereco().getLogradouro() : null,
            cliente.getEndereco() != null ? cliente.getEndereco().getBairro() : null,
            cliente.getEndereco() != null ? cliente.getEndereco().getCidade() : null,
            cliente.getEndereco() != null ? cliente.getEndereco().getUf() : null
        );
    }
}