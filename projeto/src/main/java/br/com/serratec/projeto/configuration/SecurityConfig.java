package br.com.serratec.projeto.configuration;

import java.util.Arrays;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import br.com.serratec.projeto.security.JwtAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity // Permite usar @PreAuthorize nos Controllers
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthFilter;
    private final UserDetailsService userDetailsService;

    // Notas: O 'UserDetailsService' aqui usa a classe 'CustomUserDetailsService'
    public SecurityConfig(JwtAuthenticationFilter jwtAuthFilter, UserDetailsService userDetailsService) {
        this.jwtAuthFilter = jwtAuthFilter;
        this.userDetailsService = userDetailsService;
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // CSRF é desnecessário em APIs REST Stateless com JWT
            
            // Permite que o console do H2 abra no navegador (desliga o bloqueio de frames)
            .headers(headers -> headers.frameOptions(frame -> frame.disable())) 
            
            // Configura a API para não guardar sessão na memória (Regra de Ouro do JWT)
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            
            // Mapeamento de Rotas
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/h2-console/**", "/auth/**", "/swagger-ui/**", "/v3/api-docs/**").permitAll()
				.requestMatchers("/auth/").permitAll()
	            .requestMatchers(HttpMethod.GET,"/funcionarios").permitAll()
	            .requestMatchers(HttpMethod.POST,"/perfis").permitAll()
	            .requestMatchers("/h2-console/**").permitAll()
	            .requestMatchers(HttpMethod.POST,"/usuarios").permitAll()
	            .requestMatchers(HttpMethod.GET,"/usuarios").hasRole("ADMIN")
	            .requestMatchers(HttpMethod.GET, "/funcionarios/*/foto").hasAnyRole("ADMIN", "COMPRAS","RH")
	            .requestMatchers(HttpMethod.POST, "/funcionarios").hasAnyRole("ADMIN", "COMPRAS","RH")
                .anyRequest().authenticated() // Qualquer outra rota exige Token!
            )
            
            // Regista o Provedor de Autenticação e o Filtro criado acima
            .authenticationProvider(authenticationProvider())
            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @@Bean
    public AuthenticationProvider authenticationProvider() {
        // 1. Cria a instância VAZIA (Sem nada dentro dos parênteses!)
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        
        // 2. Ensina o Spring a ir buscar o utilizador no banco de dados (Volte com esta linha!)
        authProvider.setUserDetailsService(userDetailsService); 
        
        // 3. Injeta o algoritmo de criptografia da senha (BCrypt)
        authProvider.setPasswordEncoder(passwordEncoder());
        
        return authProvider;
    }

    // O Gerente de Autenticação utilizado lá no AuthController para fazer o Login.
    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    // O algoritmo criptográfico.
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

	@Bean
	CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration corsConfiguration = new CorsConfiguration();
		corsConfiguration.setAllowedOrigins(Arrays.asList("http://localhost:5173", "http://localhost:2000"));
		corsConfiguration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS", "HEAD"));
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", corsConfiguration.applyPermitDefaultValues());
		return source;
	}

}
