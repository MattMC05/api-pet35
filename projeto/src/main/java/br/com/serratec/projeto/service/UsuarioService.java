package br.com.serratec.projeto.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import br.com.serratec.projeto.dto.UsuarioRequestDTO;
import br.com.serratec.projeto.dto.UsuarioResponseDTO;
import br.com.serratec.projeto.model.Usuario;
import br.com.serratec.projeto.repository.UsuarioRepository;

@Service
public class UsuarioService {

    private final UsuarioRepository repository;
    private final PasswordEncoder encoder;

    public UsuarioService(UsuarioRepository repository, PasswordEncoder encoder) {
        this.repository = repository;
        this.encoder = encoder;
    }

    @Transactional
    public UsuarioResponseDTO registrar(UsuarioRequestDTO dto) {
        if (repository.existsByEmail(dto.email())) {
            throw new IllegalArgumentException("Este e-mail já está em uso.");
        }

        Usuario novoUsuario = new Usuario(
            dto.nome(), 
            dto.email(), 
            encoder.encode(dto.senha()), 
            null
        );
        
        return new UsuarioResponseDTO(repository.save(novoUsuario));
    }

    @Transactional
    public void processarFalhaLogin(String email) {
        repository.findByEmail(email).ifPresent(usuario -> {
            usuario.registrarFalhaLogin();
            repository.save(usuario);
        });
    }

    @Transactional
    public void resetarFalhasLogin(String email) {
        repository.findByEmail(email).ifPresent(usuario -> {
            usuario.resetarTentativas();
            repository.save(usuario);
        });
    }

    @Transactional
    public void inativarUsuario(Long id) {
        repository.findById(id).ifPresent(usuario -> {
            usuario.desativar();
            repository.save(usuario);
        });
    }
    
}