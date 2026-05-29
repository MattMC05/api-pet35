package br.com.serratec.projeto.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record VeiculoRequestDTO(

    @NotBlank(message = "Placa é obrigatória")
    @Pattern(regexp = "[A-Z]{3}\\d{4}", message = "Placa inválida (ex: ABC1234)")
    String placa,

    @NotBlank(message = "Marca é obrigatória")
    String marca,

    @NotBlank(message = "Modelo é obrigatório")
    String modelo,

    @NotNull(message = "Ano é obrigatório")
    @Min(value = 1970, message = "Ano deve ser maior que 1970")
    @Max(value = 2026, message = "Ano deve ser menor que 2026")
    Integer ano,

    @NotBlank(message = "Cor é obrigatória")
    String cor,

    @NotNull(message = "ID do cliente é obrigatório")
    Long clienteId // Vínculo obrigatório com o dono
) 

{
    // Construtor que padroniza a placa para maiúsculas e remove espaços
    public VeiculoRequestDTO {
        placa = (placa != null) ? placa.replaceAll("\\s+", "").toUpperCase() : null;
    }
    
}