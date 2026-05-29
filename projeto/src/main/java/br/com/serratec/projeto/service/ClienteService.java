package br.com.serratec.projeto.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import br.com.serratec.projeto.configuration.MailConfig;
import br.com.serratec.projeto.dto.ClienteResponseDTO;
import br.com.serratec.projeto.dto.EnderecoResponseDTO;
import br.com.serratec.projeto.exceptions.ClienteEmailException;
import br.com.serratec.projeto.exceptions.EnderecoException;
import br.com.serratec.projeto.exceptions.ResourceNotFoundException;
import br.com.serratec.projeto.model.Cliente;
import br.com.serratec.projeto.model.Endereco;
import br.com.serratec.projeto.repository.ClienteRepository;
import br.com.serratec.projeto.repository.EnderecoRepository;
import jakarta.transaction.Transactional;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private EnderecoRepository enRepository;

    @Autowired
    private MailConfig mailConfig;

    @Transactional
    public ClienteResponseDTO inserir(Cliente cliente){
        Cliente clienteBanco = clienteRepository.findByEmail(cliente.getEmail());
        if (clienteBanco != null) {
            throw new ClienteEmailException("Email já cadastrado");
        }
        Endereco enderecoBanco = enRepository.findByCep(cliente.getEndereco().getCep());
        if (enderecoBanco != null) {
            cliente.setEndereco(enderecoBanco);
            clienteRepository.save(cliente);
        }else{
        clienteRepository.save(cliente);
        buscarCep(cliente.getEndereco().getCep());
        }
        mailConfig.sendMail(cliente.getEmail(), "Cadastro de cliente", cliente.toString());
        return new ClienteResponseDTO(cliente);
    }



    private EnderecoResponseDTO buscarCep(String cep){
        RestTemplate restTemplate = new RestTemplate();
        String url = "https://viacep.com.br/ws/"+cep+"/json/";
        Endereco enderecoViaCep = restTemplate.getForObject(url, Endereco.class);
        if (enderecoViaCep != null) {
            enderecoViaCep.setCep(enderecoViaCep.getCep().replaceAll("-", ""));
            return inserir(enderecoViaCep);
        }
        throw new EnderecoException("Cep não encontrado!");
    }

    private EnderecoResponseDTO inserir(Endereco enderecoViaCep) {
        return new EnderecoResponseDTO(enRepository.save(enderecoViaCep));
    }

    public List<ClienteResponseDTO> listarTodos(){
        return clienteRepository.findAll().stream()
        .map(cliente -> new ClienteResponseDTO(cliente))
        .collect(Collectors.toList());
    }

    public Cliente alterar(Long id, Cliente cliente){
        if (clienteRepository.existsById(id)) {
            Cliente clienteExistente = clienteRepository.findById(id).get();
            cliente.setId(id);
            if (cliente.getNome() == null) {
                cliente.setNome(clienteExistente.getNome());
            }
            if (cliente.getTelefone() == null) {
                cliente.setTelefone(clienteExistente.getTelefone());
            }
            if (cliente.getEmail() == null) {
                cliente.setEmail(clienteExistente.getEmail());
            }
            if (cliente.getCpf() == null) {
                cliente.setCpf(clienteExistente.getCpf());
            }
            if (cliente.getEndereco() == null) {
                cliente.setEndereco(clienteExistente.getEndereco());
            }
            mailConfig.sendMail(cliente.getEmail(), "Atualização de dados do cliente", cliente.toString());
            return clienteRepository.save(cliente);
        }
        throw new ResourceNotFoundException("Cliente não encontrado");
    }

    public void apagar(Long id){
        if (clienteRepository.existsById(id)) {
            clienteRepository.deleteById(id);
        }else{
            throw new ResourceNotFoundException("Cliente não encontrado");
        }
    }

}
