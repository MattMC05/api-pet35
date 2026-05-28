package br.com.serratec.projeto.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;

@Configuration
public class AppConfig {

    private final UserDetailsService userDetailsService;

    public AppConfig(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

}