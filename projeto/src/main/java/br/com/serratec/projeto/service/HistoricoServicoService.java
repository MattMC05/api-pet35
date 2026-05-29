package br.com.serratec.projeto.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.serratec.projeto.dto.HistoricoServicoResponseDTO;
import br.com.serratec.projeto.exceptions.ResourceNotFoundException;
import br.com.serratec.projeto.model.HistoricoServico;
import br.com.serratec.projeto.model.Servico;
import br.com.serratec.projeto.repository.HistoricoServicoRepository;
import jakarta.transaction.Transactional;

@Service
public class HistoricoServicoService {
    
    @Autowired
    private HistoricoServicoRepository repository;

    @Transactional
    public void registrarOperacao(Servico servico, String operacao) {
        HistoricoServico historico = new HistoricoServico(servico, operacao, LocalDateTime.now());
        repository.save(historico);
    }

    public List<HistoricoServicoResponseDTO> listarHistoricoPorServico(Long servicoId) {
        return repository.findByServicoId(servicoId)
            .stream()
            .map(HistoricoServicoResponseDTO::new)
            .collect(Collectors.toList());
    }

    public List<HistoricoServicoResponseDTO> listarTodoHistorico() {
        return repository.findAll()
            .stream()
            .map(HistoricoServicoResponseDTO::new)
            .collect(Collectors.toList());
    }

    @Transactional
    public void deletarHistoricoServico(Long servicoId) {
        List<HistoricoServico> historicos = repository.findByServicoId(servicoId);
        if (historicos.isEmpty()) {
            throw new ResourceNotFoundException("Nenhum histórico encontrado para o serviço com ID: " + servicoId);
        }
        repository.deleteAll(historicos);
    }

    @Transactional
    public void deletarRegistroHistorico(Long historicoId) {
        if (!repository.existsById(historicoId)) {
            throw new ResourceNotFoundException("Registro de histórico não encontrado com ID: " + historicoId);
        }
        repository.deleteById(historicoId);
    }
}
