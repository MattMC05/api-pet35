package br.com.serratec.projeto.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.serratec.projeto.repository.UsuarioRepository;

//Serviço exclusivo para integração com o Spring Security. A sua única responsabilidade é ir à base de dados,
// validar se a credencial existe e devolver o contrato UserDetails para a geração ou validação do Token JWT.
@Service
public record CustomUserDetailsService(UsuarioRepository repository) implements UserDetailsService {

    // O uso do 'record' elimina a necessidade de construtores verbosos e do perigoso @Autowired.
    // A dependência (repository) nasce como 'final' e imutável, garantindo Thread-Safety.
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        
        // Busca no banco e lança a exceção exata do Spring Security imediatamente se falhar.
        // O Spring Security captura esta exceção internamente e traduz para um HTTP 401 ou 403 automaticamente.
        return repository.findByEmail(username) // Se o seu login for o campo 'usuario', mude para findByUsuario(username)
                .orElseThrow(() -> new UsernameNotFoundException("Falha na autenticação: Credenciais inválidas para o login fornecido."));
                
        // Nota de Segurança (ATENÇÂO AQUI): Nunca retorne "Usuário X não encontrado". 
        // Mensagens genéricas como "Credenciais inválidas" evitam ataques de enumeração de utilizadores (User Enumeration).
    }
    
}
