package br.com.serratec.projeto.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.serratec.projeto.dto.ServicoResponseDTO;
import br.com.serratec.projeto.model.Servico;
import br.com.serratec.projeto.repository.ServicoRepository;
import jakarta.transaction.Transactional;

@Service
public class ServicoService {
    @Autowired
    private ServicoRepository repository;

    @Transactional
    public ServicoResponseDTO inserir(Servico ordemServico){
        repository.save(ordemServico);
        return new ServicoResponseDTO(ordemServico);
    }

    public List<ServicoResponseDTO> listarTodos(){
        return repository.findAll().stream()
        .map(servico -> new ServicoResponseDTO(servico))
        .collect(Collectors.toList());
    }

    public ServicoResponseDTO alterar( Long id, Servico ordemServico){
        if (repository.existsById(id)) {
            ordemServico.setId(id);
            repository.save(ordemServico);
            return new ServicoResponseDTO(ordemServico);
        }
        return null;
    }
}
