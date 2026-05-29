package br.com.serratec.projeto.service;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import br.com.serratec.projeto.dto.ModeloNotificacaoRequestDTO;
import br.com.serratec.projeto.dto.ModeloNotificacaoResponseDTO;
import br.com.serratec.projeto.model.ModeloNotificacao;
import br.com.serratec.projeto.model.OrdemDeServico;
import br.com.serratec.projeto.repository.ModeloNotificacaoRepository;
import jakarta.transaction.Transactional;

@Service
public class ModeloNotificacaoService {

    private final EmailService emailService;
    private final ModeloNotificacaoRepository repository;

    public ModeloNotificacaoService(ModeloNotificacaoRepository repository, EmailService emailService) {
        this.repository = repository;
        this.emailService = emailService;
    }

    // CRUD

    public List<ModeloNotificacaoResponseDTO> listarTodos() {
        return repository.findAll().stream().map(ModeloNotificacaoResponseDTO::new).collect(Collectors.toList()); 
    }

    @Transactional
    public ModeloNotificacaoResponseDTO inserir(ModeloNotificacaoRequestDTO dto) {
        ModeloNotificacao modelo = new ModeloNotificacao(dto.OsStatus(), dto.assunto(), dto.textoBase());
        return new ModeloNotificacaoResponseDTO(repository.save(modelo));
    }

    @Transactional
    public ModeloNotificacaoResponseDTO atualizar(Long id, ModeloNotificacaoRequestDTO dto) {
        return repository.findById(id).map(modelo -> {
            modelo.setstatusOs(dto.OsStatus());
            modelo.setAssunto(dto.assunto());
            modelo.setTextoBase(dto.textoBase());

            return new ModeloNotificacaoResponseDTO(repository.save(modelo));
        }).orElseThrow(() -> new IllegalArgumentException("Modelo não encontrado"));
    }

    @Transactional
    public void deletar(Long id) {
        repository.deleteById(id);
    }

    public void notificarMudancaStatus(OrdemDeServico os) {

        repository.findByStatusOs(os.getStatus().toString()).ifPresent(modelo -> {
            
            // Troca as tags dinâmicas pelos dados reais do cliente e do veículo
            String textoFinal = modelo.getTextoBase()
                .replace("[NOME]", os.getCliente().getNome())
                .replace("[VEICULO]", os.getVeiculo().getPlaca())
                .replace("[STATUS]", os.getStatus().toString());

            // Dispara o e-mail
            emailService.enviarEmail(os.getCliente().getEmail(), modelo.getAssunto(), textoFinal);
        });
    }
}