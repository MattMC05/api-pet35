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
    private final ModeloNotificacaoService modeloNotificacaoService;
    @Autowired
    private OrdemServicoRepository repository;

    OrdemServicoService(ModeloNotificacaoService modeloNotificacaoService) {
        this.modeloNotificacaoService = modeloNotificacaoService;
    }

    @Transactional
    public OrdemServicoResponseDTO inserir(OrdemDeServico ordemServico) {
        repository.save(ordemServico);
        return new OrdemServicoResponseDTO(ordemServico);
    }

    public List<OrdemServicoResponseDTO> listarTodos() {
        return repository.findAll().stream()
                .map(ordemServico -> new OrdemServicoResponseDTO(ordemServico))
                .collect(Collectors.toList());
    }

    @Transactional
    public OrdemServicoResponseDTO alterar(Long id, OrdemDeServico ordemServicoAlterada) {
        return repository.findById(id).map(osExistente -> {
            String statusAntigo = osExistente.getStatus() != null ? osExistente.getStatus().toString() : "";
            String statusNovo = ordemServicoAlterada.getStatus() != null ? ordemServicoAlterada.getStatus().toString()
                : "";
            osExistente.setDescricao(ordemServicoAlterada.getDescricao());
            osExistente.setValor(ordemServicoAlterada.getValorTotal());
            osExistente.setStatus(ordemServicoAlterada.getStatus());
            OrdemDeServico osSalva = repository.save(osExistente);
            if (!statusAntigo.equals(statusNovo)) {
                modeloNotificacaoService.notificarMudancaStatus(osSalva);
            }
            return new OrdemServicoResponseDTO(osSalva);

        }).orElseThrow(() -> new IllegalArgumentException("Ordem de Serviço não encontrada com o ID: " + id));
    }

    public void apagar(Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
        } else {
            throw new ResourceNotFoundException("\"Ordem de Serviço não encontrada com o ID: \" + id");
        }
    }
}
