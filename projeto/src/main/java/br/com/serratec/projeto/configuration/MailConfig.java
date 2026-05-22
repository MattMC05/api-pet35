package br.com.serratec.projeto.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

@Configuration
public class MailConfig {
    @Value("${USUARIO_EMAIL}")
    private String email;

    @Autowired
    private JavaMailSender javaMailSender;

    public void sendMail(String para, String assunto, String texto){
        SimpleMailMessage mensagem = new SimpleMailMessage();
        mensagem.setFrom(email);
        mensagem.setTo(para);
        mensagem.setSubject(assunto);
        mensagem.setText("Dados do Cadastro!\n"+texto+"\n\n\n Residência 2026");
        javaMailSender.send(mensagem);
    }
}
