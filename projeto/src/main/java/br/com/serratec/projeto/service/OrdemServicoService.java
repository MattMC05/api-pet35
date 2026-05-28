package br.com.serratec.projeto.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.serratec.projeto.dto.OrdemServicoResponseDTO;
import br.com.serratec.projeto.exceptions.ResourceNotFoundException;
import br.com.serratec.projeto.model.OrdemDeServico;
import br.com.serratec.projeto.repository.OrdemServicoRepository;
import jakarta.transaction.Transactional;

@Service
public class OrdemServicoService {
    @Autowired
    private OrdemServicoRepository repository;
    
    @Transactional
    public OrdemServicoResponseDTO inserir(OrdemDeServico ordemServico){
        repository.save(ordemServico);
        return new OrdemServicoResponseDTO(ordemServico);
    }

    public List<OrdemServicoResponseDTO> listarTodos(){
        return repository.findAll().stream()
        .map(ordemServico -> new OrdemServicoResponseDTO(ordemServico))
        .collect(Collectors.toList());
    }

    public OrdemServicoResponseDTO alterar( Long id, OrdemDeServico ordemServico){
        if (repository.existsById(id)) {
            ordemServico.setId(id);
            repository.save(ordemServico);
            return new OrdemServicoResponseDTO(ordemServico);
        }
        return null;
    }

    public void apagar(Long id){
        if (repository.existsById(id)) {
            repository.deleteById(id);
        }else{
            throw new ResourceNotFoundException("Ordem de Serviço não encontrada");
        }
    }
}
