package br.com.serratec.projeto.security;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {

    // Injeção limpa via application.properties com fallback seguro
    @Value("${auth.jwt-secret:minha_chave_super_secreta_padrao_para_oficina_2026_xpto_com_mais_de_32_bytes}")
    private String secret;

    @Value("${auth.jwt-expiration-miliseg:86400000}") // 24 horas de validade do token
    private long expiration;

    // Monta a chave criptográfica segura exigida pelo JJWT moderno.
    private SecretKey getChaveAssinatura() {
        return Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    // Gera o token a partir do e-mail/username do utilizador.
    public String generateToken(String username) {
        return Jwts.builder()
                .subject(username)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getChaveAssinatura())
                .compact();
    }

    // Extrai o username (e-mail) de dentro do Token. Retorna null se o token for inválido, adulterado ou estiver expirado.
    public String extractUsername(String token) {
        try {
            return Jwts.parser()
                    .verifyWith(getChaveAssinatura())
                    .build()
                    .parseSignedClaims(token)
                    .getPayload()
                    .getSubject();
        } catch (JwtException | IllegalArgumentException e) {
            return null; // Captura silenciosa de tokens inválidos
        }
    }

    // Valida se o token pertence ao utilizador e se ainda está no prazo de validade.
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username != null && username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    // Checa se a data de expiração do token é anterior à data atual.
    private boolean isTokenExpired(String token) {
        try {
            Date expirationDate = Jwts.parser()
                    .verifyWith(getChaveAssinatura())
                    .build()
                    .parseSignedClaims(token)
                    .getPayload()
                    .getExpiration();
            return expirationDate.before(new Date());
        } catch (Exception e) {
            return true; // Se falhar ao ler a data, assume como expirado por segurança
        }
    }

}