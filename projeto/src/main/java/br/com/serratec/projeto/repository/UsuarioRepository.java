package br.com.serratec.projeto.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import br.com.serratec.projeto.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    
    // Usado pelo Spring Security para carregar o usuário durante o login e a validação do Token
    Optional<Usuario> findByEmail(String email);

    // Proteção de Infraestrutura: Validação rápida antes do cadastro para evitar duplicação e erros 500
    boolean existsByEmail(String email);
}