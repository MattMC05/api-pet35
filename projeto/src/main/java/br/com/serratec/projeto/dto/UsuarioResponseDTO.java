package br.com.serratec.projeto.dto;

import br.com.serratec.projeto.model.Usuario;

public record UsuarioResponseDTO(Long id, String nome, String email) {
    
    public UsuarioResponseDTO(Usuario u) {
        this(u.getId(), u.getNome(), u.getUsername());
    }
}