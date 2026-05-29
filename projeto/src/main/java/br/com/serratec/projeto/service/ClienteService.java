package br.com.serratec.projeto.service;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import br.com.serratec.projeto.dto.ClienteRequestDTO;
import br.com.serratec.projeto.dto.ClienteResponseDTO;
import br.com.serratec.projeto.dto.ViaCepDTO;
import br.com.serratec.projeto.model.Cliente;
import br.com.serratec.projeto.repository.ClienteRepository;

@Service
public class ClienteService {

    private final ClienteRepository repository;
    private final EmailService emailService;
    private final ViaCepService viaCepService;

    public ClienteService(ClienteRepository repository, EmailService emailService, ViaCepService viaCepService) {
        this.repository = repository;
        this.emailService = emailService;
        this.viaCepService = viaCepService;
    }

    public List<ClienteResponseDTO> listarTodos() {
        return repository.findAll().stream()
                .map(ClienteResponseDTO::new)
                .collect(Collectors.toList());
    }

    public ClienteResponseDTO buscarPorId(Long id) {
        return repository.findById(id)
                .map(ClienteResponseDTO::new)
                .orElseThrow(() -> new IllegalArgumentException("Cliente não encontrado com o ID: " + id));
    }

    @Transactional
    public ClienteResponseDTO inserir(ClienteRequestDTO dto) {
        Cliente cliente = new Cliente();
        copiarDadosBase(dto, cliente);
        preencherEndereco(cliente, dto.cep());
        
        Cliente clienteSalvo = repository.save(cliente);

        enviarNotificacao(clienteSalvo.getEmail(), clienteSalvo.getNome(), "Cadastro Realizado", 
            "O seu registo foi concluído com sucesso. O seu endereço registado é: " + clienteSalvo.getEndereco + ".");

        return new ClienteResponseDTO(clienteSalvo);
    }

    @Transactional
    public ClienteResponseDTO atualizar(Long id, ClienteRequestDTO dto) {
        return repository.findById(id).map(clienteExistente -> {
            
            if (dto.cep() != null && !dto.cep().equals(clienteExistente.getCep())) {
                preencherEndereco(clienteExistente, dto.cep());
            }

            copiarDadosBase(dto, clienteExistente);
            Cliente clienteSalvo = repository.save(clienteExistente);

            enviarNotificacao(clienteSalvo.getEmail(), clienteSalvo.getNome(), "Cadastro Atualizado", 
                "Os seus dados cadastrais foram atualizados com sucesso no nosso sistema.");

            return new ClienteResponseDTO(clienteSalvo);

        }).orElseThrow(() -> new IllegalArgumentException("Cliente não encontrado com o ID: " + id));
    }

    @Transactional
    public void deletar(Long id) {
        if (!repository.existsById(id)) {
            throw new IllegalArgumentException("Cliente não encontrado com o ID: " + id);
        }
        repository.deleteById(id);
    }

    private void preencherEndereco(Cliente cliente, String cep) {
        ViaCepDTO endereco = viaCepService.consultarCep(cep);
        if (endereco != null && endereco.cep() != null) {
            cliente.setC(cep);
            cliente.setLogradouro(endereco.logradouro());
            cliente.setBairro(endereco.bairro());
            cliente.setCidade(endereco.localidade());
            cliente.setUf(endereco.uf());
        } else {
            throw new IllegalArgumentException("CEP inválido ou não encontrado: " + cep);
        }
    }

    private void copiarDadosBase(ClienteRequestDTO dto, Cliente cliente) {
        cliente.setNome(dto.nome());
        cliente.setEmail(dto.email());
        cliente.setTelefone(dto.telefone());
        cliente.setCpf(dto.cpf());
    }

    private void enviarNotificacao(String destinatario, String nome, String titulo, String corpoMensagem) {
        String assunto = "Oficina Mecânica - " + titulo;
        String texto = "Olá " + nome + ",\n\n" + corpoMensagem + "\n\nAgradecemos a confiança!";
        emailService.enviarEmail(destinatario, assunto, texto);
    }
}