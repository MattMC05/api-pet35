package br.com.serratec.projeto.configuration;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

    @Value("${jwt.secret:minha_chave_super_secreta_padrao_para_oficina_2026_xpto_com_mais_de_32_bytes}")
    private String secret;

    @Value("${jwt.expiration:86400000}") // Padrão: 24 horas em milissegundos
    private long tempoExpiracao;

    // Transforma a String da chave secreta num objeto SecretKey criptograficamente seguro,exigido pelas versões modernas da biblioteca JJWT.
    private SecretKey getChaveAssinatura() {
        return Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    // Gera o Token JWT com base no e-mail do utilizador autenticado.
    public String gerarToken(String email) {
        Date agora = new Date();
        Date validade = new Date(agora.getTime() + tempoExpiracao);

        return Jwts.builder()
                .subject(email) // O dono do token (Payload principal)
                .issuedAt(agora) // Data de criação
                .expiration(validade) // Data de expiração
                .signWith(getChaveAssinatura()) // Assinatura digital (A prova de falsificação)
                .compact();
    }

    // Valida o token e extrai o e-mail se ele for autêntico e não estiver expirado.
    // Retorna NULL se qualquer coisa estiver errada, bloqueando o acesso silenciosamente.
    public String validarExtrairEmail(String token) {
        try {
            return Jwts.parser()
                    .verifyWith(getChaveAssinatura()) // Valida a assinatura com a nossa chave
                    .build()
                    .parseSignedClaims(token) // Lê o conteúdo
                    .getPayload()
                    .getSubject(); // Devolve o E-mail salvo no Payload
                    
        } catch (JwtException | IllegalArgumentException e) {
            // Se o token estiver expirado, alterado ou malformado, cai aqui.
            // Retornamos null para o Filtro barrar a requisição com Erro 401.
            return null;
        }
    }
}