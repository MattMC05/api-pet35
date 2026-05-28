package br.com.serratec.projeto.security;

import java.io.IOException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

    private final JwtUtil jwtUtil;
    private final UserDetailsService userDetailsService;

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager, JwtUtil jwtUtil,
            UserDetailsService userDetailsService) {
        super(authenticationManager);
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        
        String header = request.getHeader("Authorization");

        if (header != null && header.startsWith("Bearer ")) {
            String token = header.substring(7);
            
            System.out.println("\n--- [INÍCIO DO FILTRO JWT] ---");
            System.out.println("Token recebido na requisição: " + token);

            UsernamePasswordAuthenticationToken auth = getAuthentication(token);
            System.out.println("-> Teste1: Método getAuthentication finalizado.");

            if (auth != null) {
                SecurityContextHolder.getContext().setAuthentication(auth);
                System.out.println("-> Teste2: Usuário autenticado com SUCESSO no Contexto do Spring Security!");
            } else {
                System.out.println("-> Teste Falhou: Autenticação retornou NULL (Token inválido/expirado).");
            }
            System.out.println("--- [FIM DO FILTRO JWT] ---\n");
        }
        
        // Permite que a requisição siga para o próximo filtro ou Controller
        chain.doFilter(request, response);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(String token) {
        // Extrai o username de dentro do token
        String username = jwtUtil.extractUsername(token);
        System.out.println("-> Teste2.1: Username extraído do token: " + username);

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            
            // Busca os detalhes e permissões do usuário no banco de dados
            UserDetails user = userDetailsService.loadUserByUsername(username);
            System.out.println("-> Teste3: Usuário encontrado no banco de dados: " + user.getUsername());

            // 3. Valida se o token realmente pertence a este usuário e se não expirou
            if (jwtUtil.isTokenValid(token, user)) {
                System.out.println("-> Teste4: Validação do Token confirmada (isTokenValid = true).");
                return new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
            } else {
                System.out.println("-> Teste Erro: isTokenValid retornou FALSE.");
            }
        }
        return null;
    }
    
}