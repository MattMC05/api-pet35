package br.com.serratec.projeto.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.serratec.projeto.dto.OrdemServicoResponseDTO;
import br.com.serratec.projeto.exceptions.ResourceNotFoundException;
import br.com.serratec.projeto.model.ItemOs;
import br.com.serratec.projeto.model.OrdemDeServico;
import br.com.serratec.projeto.repository.ItemOsRepository;
import br.com.serratec.projeto.repository.OrdemServicoRepository;
import jakarta.transaction.Transactional;

@Service
public class OrdemServicoService {
    @Autowired
    private OrdemServicoRepository repository;

    @Autowired
    private ItemOsRepository itemRepository;

    @Autowired
    private ServicoService sService;
    
    @Transactional
    public OrdemServicoResponseDTO inserir(OrdemDeServico ordemServico){
        for (ItemOs ios : ordemServico.getItemsOs()) {
            ios.setOrdemDeServico(ordemServico);
            ios.setServico(sService.buscar(ios.getServico().getId()).get());
            ios.setDesconto(ios.getDesconto());
            ios.setQuantidade(ios.getQuantidade());
            ios.setSubtotal(ios.getSubtotal());
        }
        repository.save(ordemServico);
        itemRepository.saveAll(ordemServico.getItemsOs());
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
