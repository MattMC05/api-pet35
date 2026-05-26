package br.com.serratec.projeto.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import br.com.serratec.projeto.enums.*;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "usuarios")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED) // Cria o construtor vazio exigido pelo JPA como "protected"
@EqualsAndHashCode(of = "id") // JPA: Equals/HashCode baseados APENAS no ID para uso seguro em Collections (Set/List)
public class Usuario implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(unique = true)
    @NotBlank(message = "O e-mail é obrigatório")
    @Email(message = "Formato de e-mail inválido")
    private String email;

    @NotBlank(message = "A senha é obrigatória")
    @Size(min = 6, message = "A senha deve ter no mínimo 6 caracteres")
    private String senha;

    // Mapeia o Enum como String no banco (ex: salva "ROLE_ADMIN" em vez de "0")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PerfilAcesso perfil;

    @Column(nullable = false)
    private boolean ativo = true;

    public void desativar() { this.ativo = false; }
    public void ativar() { this.ativo = true; }

    @CreatedDate
    @Column(name = "data_criacao", updatable = false)
    private LocalDateTime dataCriacao;

    @Column(name = "tentativas_login")
    private int tentativasLogin = 0;

    @Column(name = "bloqueado_ate")
    private LocalDateTime bloqueadoAte;

    public void registrarFalhaLogin() {
        this.tentativasLogin++;
        if (this.tentativasLogin >= 5) {
            this.bloqueadoAte = LocalDateTime.now().plusMinutes(15); // Bloqueia por 15 minutos
        }
    }

    public void resetarTentativas() {
        this.tentativasLogin = 0;
        this.bloqueadoAte = null;
    }

    // Construtor Seguro (Sem expor o ID e forçando regras na criação)
    public Usuario(String nome, String email, String senhaCriptografada, PerfilAcesso perfil) {
        this.nome = nome;
        this.email = email;
        this.senha = senhaCriptografada;
        this.perfil = (perfil != null) ? perfil : PerfilAcesso.ROLE_MECANICO; // Fallback seguro
    }

    // Encapsulamento: Única forma de alterar a senha de fora da classe
    public void atualizarSenha(String novaSenhaCriptografada) {
        this.senha = novaSenhaCriptografada;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Converte o Perfil salvo no banco em uma autoridade do Spring Security
        return List.of(new SimpleGrantedAuthority(this.perfil.name()));
    }

    @Override
    public String getPassword() {
        return this.senha;

    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.ativo; // Se o usuário for desativado pelo administrador, o Spring barra o login na hora.
    }
    
    @Override 
    public boolean isAccountNonLocked() {
        // Se a data de bloqueio passou (ou é nula), a conta está liberada
        if (this.bloqueadoAte == null) return true;
        return LocalDateTime.now().isAfter(this.bloqueadoAte);
    }

}