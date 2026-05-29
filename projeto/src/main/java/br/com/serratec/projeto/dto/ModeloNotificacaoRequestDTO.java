package br.com.serratec.projeto.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ModeloNotificacaoRequestDTO(
    
    @NotNull
    Long id,

    @NotBlank
    String OsStatus,

    @NotBlank
    String assunto,
    
    @NotBlank
    String textoBase
) {

}

