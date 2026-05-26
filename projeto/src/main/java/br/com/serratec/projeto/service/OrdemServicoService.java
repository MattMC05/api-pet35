package br.com.serratec.projeto.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.serratec.projeto.model.OrdemDeServico;
import br.com.serratec.projeto.repository.OrdemServicoRepository;

@Service
public class OrdemServicoService {
    @Autowired
    private OrdemServicoRepository repository;

    public OrdemDeServico inserir(OrdemDeServico ordemServico){
        return repository.save(ordemServico);
    }

    public Optional<OrdemDeServico> buscar(Long id){
        return repository.findById(id);
    }
}
