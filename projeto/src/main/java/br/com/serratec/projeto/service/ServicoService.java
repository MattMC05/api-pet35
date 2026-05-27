package br.com.serratec.projeto.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.serratec.projeto.model.Servico;
import br.com.serratec.projeto.repository.ServicoRepository;

@Service
public class ServicoService {
    @Autowired
    private ServicoRepository repository;

    public Servico inserir(Servico servico){
        return repository.save(servico);
    }

    public Optional<Servico> buscar(Long id){
        return repository.findById(id);
    }
    
}
