package br.com.serratec.exercicio1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.serratec.exercicio1.model.Consulta;
import br.com.serratec.exercicio1.repository.ConsultaRepository;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/consultas")
public class ConsultaController {
    @Autowired
    private ConsultaRepository repository;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Consulta inserir(@Valid @RequestBody Consulta consulta){
        return repository.save(consulta);
    }
    
}
