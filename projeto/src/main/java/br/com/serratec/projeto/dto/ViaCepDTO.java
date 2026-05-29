package br.com.serratec.projeto.dto;

public record ViaCepDTO(
    String cep, 
    String logradouro, 
    String bairro, 
    String localidade, // O ViaCEP chama a cidade de "localidade"
    String uf
) {

}