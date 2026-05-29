package br.com.serratec.projeto.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "modelos_notificacao")
public class ModeloNotificacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true) // Ex: "ORCAMENTO_APROVADO", "EM_MANUTENCAO", "FINALIZADA"
    private String statusOs;

    @Column(nullable = false)
    private String assunto;

    @Column(nullable = false , length = 1000)
    private String textoBase; // Aceitará tags como a [placa], [nome], [valor]

    public ModeloNotificacao () {
    }
    
    public ModeloNotificacao(String statusOs, String assunto, String textoBase) {
        this.statusOs = statusOs;
        this.assunto = assunto;
        this.textoBase = textoBase;
    }

    public Long getId() { 
        return id; 
    }
    public void setId(Long id) { 
        this.id = id; 
    }
    public String getstatusOs() { 
        return statusOs; 
    }
    
    public void setstatusOs(String statusOs) { 
        this.statusOs = statusOs; }
    public String getAssunto() { return assunto; 

    }
    public void setAssunto(String assunto) { 
        this.assunto = assunto; 
    }
    public String getTextoBase() { 
        return textoBase; 
    }
    public void setTextoBase(String textoBase) { 
        this.textoBase = textoBase;
    }
}