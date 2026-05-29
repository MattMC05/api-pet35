package br.com.serratec.projeto.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.serratec.projeto.dto.ServicoResponseDTO;
import br.com.serratec.projeto.exceptions.ResourceNotFoundException;
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

    public Optional<Servico> buscar(Long id){
        return repository.findById(id);
    }

    public Servico alterar(Long id, Servico servico){
        if (repository.existsById(id)) {
            Servico servicoExistente = repository.findById(id).get();
            servico.setId(id);
            if (servico.getDescricao() == null) {
                servico.setDescricao(servicoExistente.getDescricao());
            }
            if (servico.getValor() == null) {
                servico.setValor(servicoExistente.getValor());
            }
            if (servico.getTempoEstimado() == null) {
                servico.setTempoEstimado(servicoExistente.getTempoEstimado());
            }
            return repository.save(servico);
        }
        throw new ResourceNotFoundException("Servico não encontrado");
    }

    public void apagar(Long id){
        if (repository.existsById(id)) {
            repository.deleteById(id);
        }else{
            throw new ResourceNotFoundException("Serviço não encontrado");
        }
    }
}
