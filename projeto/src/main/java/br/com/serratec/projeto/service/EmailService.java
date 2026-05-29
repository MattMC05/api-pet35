package br.com.serratec.projeto.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final JavaMailSender mailSender;
    private final String remetente;

    public EmailService(JavaMailSender mailSender, @Value("${spring.mail.username}") String remetente) {
        this.mailSender = mailSender;
        this.remetente = remetente;
    }

    public void enviarEmail(String destinatario, String assunto, String mensagem) {
        try {
            SimpleMailMessage email = new SimpleMailMessage();
            email.setFrom(remetente);
            email.setTo(destinatario);
            email.setSubject(assunto);
            email.setText(mensagem);
            
            mailSender.send(email);
            System.out.println("✅ E-mail enviado com sucesso para: " + destinatario);
            
        } catch (Exception e) {
            // Se der erro ele avisa no console, mas NÃO quebra a API!
            System.err.println("❌ Falha ao enviar e-mail para " + destinatario + ": " + e.getMessage());
        }
    }
}