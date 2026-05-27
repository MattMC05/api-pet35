package br.com.serratec.projeto.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.serratec.projeto.dto.OrdemServicoResponseDTO;
import br.com.serratec.projeto.model.OrdemDeServico;
import br.com.serratec.projeto.repository.OrdemServicoRepository;

@Service
public class OrdemServicoService {
    @Autowired
    private OrdemServicoRepository repository;

    public OrdemDeServico inserir(OrdemDeServico ordemServico){
        return repository.save(ordemServico);
    }

    public List<OrdemServicoResponseDTO> listarTodos(){
    return repository.findAll().stream()
    .map(ordemServico -> new OrdemServicoResponseDTO(new OrdemDeServico()))
    .collect(Collectors.toList());
    }

}
