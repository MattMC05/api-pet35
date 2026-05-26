package br.com.serratec.projeto.configuration;

import java.io.IOException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.OncePerRequestFilter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity // Assim podemos usar @PreAuthorize nos Controllers
public class SecurityConfig {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    // Injeção limpa via construtor
    public SecurityConfig(JwtService jwtService, UserDetailsService userDetailsService) {
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
    }

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
            // Desativa CSRF, pois APIs REST com JWT são imunes a este ataque
            .csrf(AbstractHttpConfigurer::disable)
            
            // Define a aplicação como STATELESS (Sem sessão no servidor, 100% via Token)
            .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            
            // Mapeamento de Rotas
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/auth/**").permitAll() // Login livre
                .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll() // Documentação livre
                
                // Exemplo prático do Enum: Apenas o ADMIN pode registar novos mecânicos
                .requestMatchers(HttpMethod.POST, "/usuarios").hasRole("ADMIN")
                
                .anyRequest().authenticated() // Qualquer outra rota exige o Token
            )
            
            // Injeta o nosso filtro anónimo de JWT ANTES do filtro padrão do Spring
            .addFilterBefore(novoFiltroJwt(), UsernamePasswordAuthenticationFilter.class)
            .build();
    }

    /* Filtro JWT Embutido:
     * Mantém o código coeso. Ele lê o cabeçalho "Authorization", extrai o "Bearer",
     * valida e monta o crachá de acesso no Contexto de Segurança.*/

    private OncePerRequestFilter novoFiltroJwt() {
        return new OncePerRequestFilter() {
            @Override
            protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
                    throws ServletException, IOException {
                
                String authHeader = request.getHeader("Authorization");
                
                if (authHeader != null && authHeader.startsWith("Bearer ")) {
                    String email = jwtService.validarExtrairEmail(authHeader.substring(7));
                    
                    if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                        UserDetails usuario = userDetailsService.loadUserByUsername(email);
                        var authToken = new UsernamePasswordAuthenticationToken(usuario, null, usuario.getAuthorities());
                        SecurityContextHolder.getContext().setAuthentication(authToken);
                    }
                }
                filterChain.doFilter(request, response);
            }
        };
    }

    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}