package br.com.serratec.projeto.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import br.com.serratec.projeto.dto.AuthResponseDTO;
import br.com.serratec.projeto.dto.LoginRequestDTO;
import br.com.serratec.projeto.security.JwtUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
@Tag(name = "Autenticação", description = "Endpoint público para login e geração de JWT")
public record AuthController(
    AuthenticationManager authenticationManager,
    JwtUtil jwtUtil
) {

    @PostMapping("/login")
    @Operation(summary = "Realiza o login e devolve o Token JWT")
    public ResponseEntity<AuthResponseDTO> login(@Valid @RequestBody LoginRequestDTO loginDTO) {
        
        // 1. O AuthenticationManager verifica as credenciais no banco automaticamente
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(loginDTO.username(), loginDTO.password())
        );

        // 2. Se a senha estiver correta, gera o Token
        String token = jwtUtil.generateToken(authentication.getName());

        // 3. Devolve um JSON limpo e formatado
        return ResponseEntity.ok(new AuthResponseDTO(token));
    }
    
}