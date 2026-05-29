package br.com.serratec.projeto.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import br.com.serratec.projeto.dto.ViaCepDTO;

@Service
public class ViaCepService {

    public ViaCepDTO consultarCep(String cep) {
        // Remove traços ou espaços que o utilizador possa ter enviado
        String cepLimpo = cep.replaceAll("\\D", ""); 
        
        String url = "https://viacep.com.br/ws/" + cepLimpo + "/json/";
        
        // O RestTemplate é o "carteiro" do Spring que vai buscar os dados à API externa
        RestTemplate restTemplate = new RestTemplate();
        
        return restTemplate.getForObject(url, ViaCepDTO.class);
    }
}