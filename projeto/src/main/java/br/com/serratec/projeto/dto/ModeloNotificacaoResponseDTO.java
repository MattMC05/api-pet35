package br.com.serratec.projeto.dto;

import br.com.serratec.projeto.model.ModeloNotificacao;

public record ModeloNotificacaoResponseDTO(
    Long id, 
    String OsStatus, 
    String assunto, 
    String textoBase 
) {
    public ModeloNotificacaoResponseDTO(ModeloNotificacao modelo) {
        this(
            modelo.getId(), 
            modelo.getstatusOs(), 
            modelo.getAssunto(), 
            modelo.getTextoBase()
        ); 
    }
}